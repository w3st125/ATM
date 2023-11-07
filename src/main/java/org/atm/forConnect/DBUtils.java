package org.atm.forConnect;

import org.atm.forATM.*;


import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;


public class DBUtils {


    public static User getUserByLogin(String login) throws SQLException {
        PreparedStatement preparedStatement = addPostgreSQLConnection().prepareStatement("select user_id,user_pass,user_login from bank_user where (user_login = ?);");
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        long userId;
        String userPass;
        String userLogin;
        if (resultSet.next()) {
            userId = resultSet.getLong(1);
            userPass = resultSet.getString(2);
            userLogin = resultSet.getString(3);
            return new User(userId, userPass, userLogin);
        } else return null;
    }

    public static Account getAccountByUserID(long userID) throws SQLException {
        PreparedStatement preparedStatement = addPostgreSQLConnection().prepareStatement("select acc_user_id,acc_id,acc_balance,acc_number from account where (acc_user_id = ?);");
        preparedStatement.setLong(1, userID);
        ResultSet resultSet = preparedStatement.executeQuery();
        long userId;
        long accountId;
        BigDecimal accountBalance;
        String accountNumber;
        if (resultSet.next()) {
            userId = resultSet.getLong(1);
            accountId = resultSet.getLong(2);
            accountBalance = resultSet.getBigDecimal(3);
            accountNumber = resultSet.getString(4);
            return new Account(userId, accountId, accountBalance, accountNumber);
        } else throw new IllegalStateException("У юзера обязан быть счёт");
    }

    public static void setBalanceByAccountID(long accountID, BigDecimal sum, Connection connection) throws SQLException {
        connection.createStatement().executeUpdate("update account set acc_balance =" + sum + " where (acc_id = '" + accountID + "');");
    }

    public static void addMoneyToBalanceByAccountNumber(String accNumber, BigDecimal sum, Connection connection) throws SQLException {
        connection.createStatement().executeUpdate("update account set acc_balance = acc_balance+" + sum + " where (acc_number = '" + accNumber + "');");
    }

    public static void insertTransactionToTable(Transaction transaction, Connection connection) throws SQLException {
        connection.createStatement().execute("insert into transaction (txn_account_from, txn_account_to, txn_date, txn_type_id, txn_amount) values" +
                " ('" + transaction.getAccountFrom() + "','" + transaction.getAccountTo() + "','" + transaction.getDate() + "','" + transaction.getType().getTypeID() + "','" + transaction.getAmount() + "');");
    }

    public static void beginTransaction(Connection connection) throws SQLException {
        connection.createStatement().execute("BEGIN");
    }

    public static void commitTransaction(Connection connection) throws SQLException {
        connection.createStatement().execute("COMMIT");
    }

    public static void rollbackTransaction(Connection connection) throws SQLException {
        connection.createStatement().execute("ROLLBACK");
    }

    private static Connection addPostgreSQLConnection() throws SQLException { //todo Возможно ли сделать без паблика?
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "pgpwd4habr");
            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return conn;
    }


    public static void doP2P(User currentUser, String toNumber, BigDecimal amountTransaction) throws SQLException {
        Connection conn = addPostgreSQLConnection();
        conn.setAutoCommit(false);
        try {
            beginTransaction(conn);
            Account accountByUser = getAccountByUserID(currentUser.getId());
            BigDecimal currentUserAccountSubtract = accountByUser.getBalance().subtract(amountTransaction);
            if (currentUserAccountSubtract.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("На счёте недостаточно средств");
                return;
            }
            setBalanceByAccountID(currentUser.getId(), currentUserAccountSubtract, conn);
            addMoneyToBalanceByAccountNumber(toNumber, amountTransaction, conn);
            Transaction transaction = new Transaction(amountTransaction, accountByUser.getNumber(), toNumber, LocalDateTime.now(), TransactionType.P2P);
            insertTransactionToTable(transaction, conn);
            if (true) {
                throw new RuntimeException();
            }
            commitTransaction(conn);
        } catch (Exception e) {
            rollbackTransaction(conn);
        }


    }

    public static void doPayInCashToAccount(User currentUser) throws SQLException {
        Connection conn = addPostgreSQLConnection();
        conn.setAutoCommit(false);
        try {
            beginTransaction(conn);
            Account accountByUser = getAccountByUserID(currentUser.getId());
            BigDecimal amountTransaction = ATMUtils.inputAmount();
            BigDecimal currentUserAccountBalance = amountTransaction.add(accountByUser.getBalance());
            BankOperations.changeBalanceByAccount(accountByUser, currentUserAccountBalance, conn);
            Transaction transaction = new Transaction(amountTransaction, "ATM", accountByUser.getNumber(), LocalDateTime.now(), TransactionType.PAY_IN);
            insertTransactionToTable(transaction, conn);
            commitTransaction(conn);
        } catch (Exception e) {
            rollbackTransaction(conn);
        }
    }

    public static void doPayOutMoneyToCash(User currentUser) throws SQLException {
        Connection conn = addPostgreSQLConnection();
        conn.setAutoCommit(false);
        try {
            beginTransaction(conn);
            BigDecimal withdrawal = ATMUtils.inputAmount();
            Account accountByUser = getAccountByUserID(currentUser.getId());
            BigDecimal subtract = accountByUser.getBalance().subtract(withdrawal);
            if (subtract.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("На счёте недостаточно средств");
                return;
            }
            BankOperations.changeBalanceByAccount(accountByUser, subtract, conn);
            Transaction transaction = new Transaction(withdrawal, accountByUser.getNumber(), "ATM", LocalDateTime.now(), TransactionType.PAY_OUT);
            DBUtils.insertTransactionToTable(transaction, conn);
            commitTransaction(conn);
        } catch (Exception e) {
            rollbackTransaction(conn);
        }
    }

}


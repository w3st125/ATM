package org.atm.forConnect;

import org.atm.model.*;
import org.atm.utils.ATMUtils;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

@Service
public class DBService {

    public User getUserByLogin(String login) throws SQLException {
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

    public Account getAccountByUserID(long userID) throws SQLException {
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

    public void setBalanceByAccountID(long accountID, BigDecimal sum, Connection connection) throws SQLException {
        connection.createStatement().executeUpdate("update account set acc_balance =" + sum + " where (acc_id = '" + accountID + "');");
    }

    public void addMoneyToBalanceByAccountNumber(String accNumber, BigDecimal sum, Connection connection) throws SQLException {
        connection.createStatement().executeUpdate("update account set acc_balance = acc_balance+" + sum + " where (acc_number = '" + accNumber + "');");
    }

    public void insertTransactionToTable(Transaction transaction, Connection connection) throws SQLException {
        connection.createStatement().execute("insert into transaction (txn_account_from, txn_account_to, txn_date, txn_type_id, txn_amount) values" +
                " ('" + transaction.getAccountFrom() + "','" + transaction.getAccountTo() + "','" + transaction.getDate() + "','" + transaction.getType().getTypeID() + "','" + transaction.getAmount() + "');");
    }

    public void beginTransaction(Connection connection) throws SQLException {
        connection.createStatement().execute("BEGIN");
    }

    public void commitTransaction(Connection connection) throws SQLException {
        connection.createStatement().execute("COMMIT");
    }

    public void rollbackTransaction(Connection connection) throws SQLException {
        connection.createStatement().execute("ROLLBACK");
    }

    private Connection addPostgreSQLConnection() throws SQLException {
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


    public void doP2P(P2PRequestParams p2PRequestParams) throws SQLException {
        Long userId = p2PRequestParams.getUserId();
        String toNumber = p2PRequestParams.getNumber();
        BigDecimal amountTransaction = p2PRequestParams.getAmount();

        Connection conn = addPostgreSQLConnection();
        conn.setAutoCommit(false);
        try {
            beginTransaction(conn);
            Account accountByUser = getAccountByUserID(userId);
            BigDecimal currentUserAccountSubtract = accountByUser.getBalance().subtract(amountTransaction);
            if (currentUserAccountSubtract.compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("На счете недостаточно средств");
            }
            //setBalanceByAccountID(currentUser.getId(), currentUserAccountSubtract, conn);
            //addMoneyToBalanceByAccountNumber(toNumber, amountTransaction, conn);
            Transaction transaction = new Transaction(amountTransaction, accountByUser.getNumber(), toNumber, LocalDateTime.now(), TransactionType.P2P);
            insertTransactionToTable(transaction, conn);
            commitTransaction(conn);
        } catch (Exception e) {
            rollbackTransaction(conn);
            throw new RuntimeException(e);
        }


    }

    public void doPayInCashToAccount(PayInRequestParams payInRequestParams) throws SQLException {
        Long userId = payInRequestParams.getUserId();
        BigDecimal amount = payInRequestParams.getAmount();
        Connection conn = addPostgreSQLConnection();
        conn.setAutoCommit(false);
        try {
            beginTransaction(conn);
            Account accountByUser = getAccountByUserID(userId);
            Transaction transaction = new Transaction(amount, "atm", accountByUser.getNumber(), LocalDateTime.now(), TransactionType.PAY_IN);
            insertTransactionToTable(transaction, conn);
            commitTransaction(conn);
        } catch (Exception e) {
            rollbackTransaction(conn);
            throw new RuntimeException(e);
        }
    }

    public void doPayOutMoneyToCash(PayOutRequestParams payOutRequestParams) throws SQLException {
        BigDecimal withdrawal = payOutRequestParams.getWithdrawal();
        Long userId = payOutRequestParams.getUserId();
        Connection conn = addPostgreSQLConnection();
        conn.setAutoCommit(false);
        try {
            beginTransaction(conn);
            Account accountByUser = getAccountByUserID(userId);
            BigDecimal subtract = accountByUser.getBalance().subtract(withdrawal);
            if (subtract.compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("На счете недостаточно средств");
            }
            Transaction transaction = new Transaction(withdrawal, accountByUser.getNumber(), "atm", LocalDateTime.now(), TransactionType.PAY_OUT);
            insertTransactionToTable(transaction, conn);
            commitTransaction(conn);
        } catch (Exception e) {
            rollbackTransaction(conn);
            throw new RuntimeException(e);
        }
    }

}

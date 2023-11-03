package org.atm.forConnect;
import org.atm.forATM.Account;
import org.atm.forATM.Transaction;
import org.atm.forATM.User;


import java.math.BigDecimal;
import java.sql.*;


public class DBUtils {

    static Statement statement;


    static {
        try {
            statement = getPostgreSQLConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static User getUserByLogin(String login) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select user_id,user_pass,user_login from bank_user where (user_login = '" + login + "');");
        long userId;
        String userPass;
        String userLogin;
        if (resultSet.next()) {
            userId = resultSet.getLong(1);
            userPass = resultSet.getString(2);
            userLogin = resultSet.getString(3);
            return new User(userId, userPass, userLogin);
        }
        else return null;
    }

    public static Account getAccountByUserID(long userID ) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select acc_user_id,acc_id,acc_balance,acc_number from account where (acc_user_id = '" + userID + "');");
        long userId;
        long accountId;
        BigDecimal accountBalance;
        String accountNumber;
        if (resultSet.next()){
            userId = resultSet.getLong(1);
            accountId = resultSet.getLong(2);
            accountBalance = resultSet.getBigDecimal(3);
            accountNumber = resultSet.getString(4);
            return new Account(userId,accountId,accountBalance,accountNumber);
        }
        else throw new IllegalStateException("У юзера обязан быть счёт");
    }

    public static void setBalanceByAccountID(long accountID, BigDecimal sum) throws SQLException {
        statement.executeUpdate("update account set acc_balance ="+sum+" where (acc_id = '" + accountID + "');");
    }

    public static void addMoneyToBalanceByAccountNumber(String accNumber, BigDecimal sum) throws SQLException {
        statement.executeUpdate("update account set acc_balance = acc_balance+"+sum+" where (acc_number = '"+accNumber+"');");
    }

    public static void insertTransactionToTable(Transaction transaction) throws SQLException {
        long typeNubmer = 0;
        ResultSet resultSet = statement.executeQuery("select txnt_id from transaction_type where (txnt_name = '" + transaction.getType().toString() + "');");
        if (resultSet.next()){ typeNubmer = resultSet.getLong(1);}
        statement.execute("insert into transaction (txn_account_from, txn_account_to, txn_date, txn_type_id, txn_amount) values ('"+transaction.getAccountFrom()+"','"+transaction.getAccountTo()+"','"+transaction.getDate()+"','"+typeNubmer+"','"+transaction.getAmount()+"');");
    }

    private static Connection getPostgreSQLConnection() {
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

}


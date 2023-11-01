package org.atm.forConnect;
import org.atm.forATM.Account;
import org.atm.forATM.User;


import java.math.BigDecimal;
import java.sql.*;


public class DBUtils {

    static Statement statement;


    static {
        try {
            statement = getPostgreSQLConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static User getUserFromTable(long id) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select user_id,user_pass from users where (user_id = '" + id + "');");
        long userId;
        String userPass;
        if (resultSet.next()) {
            userId = resultSet.getLong(1);
            userPass = resultSet.getString(2);
            return new User(userId, userPass);
        }
        else return null;
    }

    public static Account getAccountFromTable(long id ) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select user_id,account_id,acc_balance from accounts where (user_id = '" + id + "');");
        long userId;
        long accountId;
        BigDecimal acc_balance;
        if (resultSet.next()){
            userId = resultSet.getLong(1);
            accountId = resultSet.getLong(2);
            acc_balance = resultSet.getBigDecimal(3);
            return new Account(userId,accountId,acc_balance);
        }
        else return null;
    }

    public static void setBalanceToTable(long id, BigDecimal sum) throws SQLException {
        statement.executeUpdate("update accounts set acc_balance ="+sum+" where (account_id = '" + id + "');");

    }

    public static Connection getPostgreSQLConnection() {
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


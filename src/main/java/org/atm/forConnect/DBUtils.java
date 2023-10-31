package org.atm.forConnect;
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
        ResultSet resultSet = statement.executeQuery("select id,pass,balance from users where (id = '" + id + "');");
        long userId;
        String userPass;
        BigDecimal userBalance;
        if (resultSet.next()) {
            userId = resultSet.getLong(1);
            userPass = resultSet.getString(2);
            userBalance = resultSet.getBigDecimal(3);
            return new User(userId, userPass, userBalance);
        }
        else return null;
    }

    public static void setBalanceToTable(long id, BigDecimal sum) throws SQLException {
        statement.executeUpdate("update users set balance ="+sum+" where (id = '" + id + "');");

    }

    public static Connection getPostgreSQLConnection() {
        Connection conn = null;
        try {
            //Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "pgpwd4habr");

            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } //catch (ClassNotFoundException e) {
        // throw new RuntimeException(e);
        // }
        return conn;
    }

}


package org.example2;

import org.example.User;

import java.sql.*;


public class PostgreSQLConnUtils {

    static Statement statement;


    static {
        try {
            statement = getPostgreSQLConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static User getUserFromTable(int id) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select id,pass,balance from users where (id = '" + id + "');");
        int userId = 0;
        int userPass = 0;
        int userBalance = 0;
        while (resultSet.next()) {
            userId = resultSet.getInt(1);
            userPass = resultSet.getInt(2);
            userBalance = resultSet.getInt(3);
        }
        User user = new User(userId, userPass, userBalance);
        return user;
    }

    public static void setBalanceToTable(int id, int sum) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from users where (id = '" + id + "');");
        resultSet.first();
        resultSet.updateInt(3,sum);
        resultSet.updateRow();
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


package com.Bank;

import java.sql.*;

public class DBConnect {

    public static Connection Embadded() throws SQLException {
        String databaseURL = "jdbc:derby:database;create=true";
        Connection connection = DriverManager.getConnection(databaseURL);
        return connection;
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = Embadded();
        String query = "SELECT * FROM ADMIN";
        Statement preparedStatement = connection.createStatement();
        preparedStatement.executeQuery(query);
        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            System.out.println(resultSet.getString("username"));
            System.out.println(resultSet.getString("birthdate"));
            System.out.println(resultSet.getString("gender"));
            System.out.println(resultSet.getString("account"));
            System.out.println(resultSet.getString("ifsc"));
            System.out.println(resultSet.getString("email"));
            System.out.println(resultSet.getString("phone"));
            System.out.println(resultSet.getString("password"));
        } else {

        }
    }
}

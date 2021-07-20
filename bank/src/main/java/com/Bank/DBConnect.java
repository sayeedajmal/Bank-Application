package com.Bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {

    public static Connection Embadded() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/BANK", "root", "PERSONAL");
        return connection;
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = Embadded();
        String query = "SELECT * FROM SAYEED";
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
            System.out.println(resultSet.getString("ammount"));
        } else {

        }
    }
}

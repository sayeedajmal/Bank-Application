package com.Bank;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {

    public static Connection Embadded() throws SQLException {
        String path = System.getProperty("user.home") + File.separator + ".config";
        path += File.separator + "database";
        File file = new File(path);
        String databaseURL = "jdbc:derby:" + file + ";create=true";
        Connection connection = DriverManager.getConnection(databaseURL);
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

package com.Bank;

import java.sql.*;

public class DBConnect {

    public static Connection Embadded() throws SQLException {
        String databaseURL = "jdbc:derby:database;create=true";
        Connection connection = DriverManager.getConnection(databaseURL);
        return connection;
    }
}

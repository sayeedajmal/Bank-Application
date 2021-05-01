package com.Bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    public static Connection Connection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/BANK", "root", "PERSONAL");
        return connection;

    }
}

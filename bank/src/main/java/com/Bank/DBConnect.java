package com.Bank;

import java.sql.*;
public class DBConnect {

    public static void main(String[] args) {
        String databaseURL = "jdbc:derby:booksdb;create=true";

        try (Connection conn = DriverManager.getConnection(databaseURL)) {
            Statement statement = conn.createStatement();

            if (!doesTableExists("book", conn)) {
                String sql = "CREATE TABLE book (book_id int primary key, title varchar(128))";
                statement.execute(sql);
                System.out.println("Created table book.");

                sql = "INSERT INTO book VALUES (1, 'Effective Java'), (2, 'Core Java')";
                statement.execute(sql);
                System.out.println("Inserted 3 rows.");
            }

            String sql = "SELECT * FROM book";
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                System.out.println(result.getString("title"));
            }

            DriverManager.getConnection("jdbc:derby:;shutdown=true");

        } catch (SQLException ex) {
            if (ex.getSQLState().equals("XJ015")) {
                System.out.println("Derby shutdown normally");
            } else {
                ex.printStackTrace();
            }
        }
    }

    private static boolean doesTableExists(String tableName, Connection conn) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet result = meta.getTables(null, null, tableName.toUpperCase(), null);

        return result.next();
    }
}

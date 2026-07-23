package com.cleaninginventory.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL =
            "jdbc:postgresql://localhost:5432/cleaning_inventory";

    private static final String USER =
            "postgres";

    private static final String PASSWORD =
            "Database123";


    public static Connection getConnection() {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("Database connected successfully!");

        } catch (SQLException e) {

            System.out.println("Database connection failed!");
            e.printStackTrace();

        }

        return connection;
    }
}

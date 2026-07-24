package com.cleaninginventory.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL =
            "jdbc:postgresql://localhost:5432/PRG381_Project"; //Change to your local connection

    private static final String USER = //Change to your local USER
            "postgres";

    private static final String PASSWORD = //Change to your local Password
            "RJord#2026";

    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL Driver registered successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL Driver NOT found!");
            System.err.println("Make sure postgresql-*.jar is in Tomcat's lib folder.");
            e.printStackTrace();
        }
    }

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
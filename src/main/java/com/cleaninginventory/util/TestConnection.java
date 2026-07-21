package com.cleaninginventory.util;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {

        Connection connection = DBConnection.getConnection();

        if (connection != null) {
            System.out.println("Test successful!");
        } else {
            System.out.println("Test failed!");
        }
    }

}

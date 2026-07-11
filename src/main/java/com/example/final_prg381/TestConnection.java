package com.example.final_prg381;

import com.example.final_prg381.util.DBConnection;

public class TestConnection {

    public static void main(String[] args) {
        try (java.sql.Connection conn = DBConnection.getConnection()) {
            System.out.println("Connected successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

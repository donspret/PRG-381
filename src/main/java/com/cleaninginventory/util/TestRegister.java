package com.cleaninginventory.util;


import com.cleaninginventory.dao.UserDAO;
import com.cleaninginventory.model.User;

public class TestRegister {


    public static void main(String[] args) {

        User user = new User();

        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@gmail.com");
        user.setUsername("test123");
        user.setPassword("Password123!");
        user.setRole("Storekeeper");



        UserDAO dao = new UserDAO();
        String result = dao.registerUser(user);  // ✅ FIXED - String

        if (result.equals("SUCCESS")) {
            System.out.println("✅ User registered successfully!");
        } else {
            System.out.println("❌ Registration failed: " + result);
        }

    }
}

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

        boolean result = dao.registerUser(user);


        if(result){
            System.out.println("User registered!");
        }
        else{
            System.out.println("Registration failed!");
        }

    }
}

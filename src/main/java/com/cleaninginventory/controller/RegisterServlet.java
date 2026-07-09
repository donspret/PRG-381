package com.cleaninginventory.controller;

import com.cleaninginventory.dao.UserDAO;
import com.cleaninginventory.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {


        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (password == null || password.length() < 8) {

            response.sendRedirect("register.jsp");
            return;

        }
        String role = request.getParameter("role");


        User user = new User();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);


        UserDAO userDAO = new UserDAO();


        boolean registered = userDAO.registerUser(user);


        if (registered) {

            response.sendRedirect("login.jsp");

        } else {

            response.sendRedirect("register.jsp");

        }

    }
}

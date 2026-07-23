package com.cleaninginventory.controller;

import com.cleaninginventory.dao.UserDAO;
import com.cleaninginventory.model.User;
import com.cleaninginventory.util.PasswordValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect("DashboardServlet");
            return;
        }
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String role = request.getParameter("role");

        if (firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty() ||
                role == null || role.trim().isEmpty()) {

            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        firstName = firstName.trim();
        lastName = lastName.trim();
        email = email.trim();
        username = username.trim();

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        String passwordError = PasswordValidator.validatePassword(password);
        if (passwordError != null) {
            request.setAttribute("error", passwordError);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        String result = userDAO.registerUser(user);

        if (result.equals("SUCCESS")) {
            request.setAttribute("success", "Registration successful! Please login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", result);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}

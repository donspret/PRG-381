package com.cleaninginventory.controller;

import com.cleaninginventory.dao.UserDAO;
import com.cleaninginventory.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

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
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {

            request.setAttribute("error", "Please enter both username and password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        User user = userDAO.loginUser(username.trim(), password.trim());

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            session.setAttribute("fullName", user.getFullName());
            session.setMaxInactiveInterval(30 * 60);

            response.sendRedirect("DashboardServlet");

        } else {
            request.setAttribute("error", "Invalid username or password. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}

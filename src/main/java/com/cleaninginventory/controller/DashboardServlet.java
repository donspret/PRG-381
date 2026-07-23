package com.cleaninginventory.controller;

import com.cleaninginventory.dao.DashboardDAO;
import com.cleaninginventory.model.DashboardStats;
import com.cleaninginventory.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {

    private DashboardDAO dashboardDAO;

    @Override
    public void init() {
        dashboardDAO = new DashboardDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get user from session
        User user = (User) session.getAttribute("user");

        // Get dashboard statistics
        DashboardStats stats = dashboardDAO.getDashboardStats();

        // Set attributes for JSP
        request.setAttribute("stats", stats);
        request.setAttribute("user", user);

        // Forward to dashboard
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}

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
        System.out.println("✅ DashboardServlet initialized!");
        dashboardDAO = new DashboardDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("✅ DashboardServlet doGet called!");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("❌ User not logged in, redirecting to login");
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        System.out.println("👤 User: " + user.getUsername());

        System.out.println("📊 Fetching dashboard statistics...");
        DashboardStats stats = dashboardDAO.getDashboardStats();
        System.out.println("📊 Stats retrieved: " + stats);

        request.setAttribute("stats", stats);
        request.setAttribute("user", user);

        System.out.println("🔄 Forwarding to dashboard.jsp");
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
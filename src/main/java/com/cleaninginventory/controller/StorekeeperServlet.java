package com.cleaninginventory.controller;

import com.cleaninginventory.dao.DashboardDAO;
import com.cleaninginventory.model.DashboardStats;
import com.cleaninginventory.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/storekeeper/dashboard")
public class StorekeeperServlet extends HttpServlet {

    private DashboardDAO dashboardDAO;

    @Override
    public void init() {
        dashboardDAO = new DashboardDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!"STOREKEEPER".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/DashboardServlet");
            return;
        }

        DashboardStats stats = dashboardDAO.getDashboardStats();
        request.setAttribute("stats", stats);
        request.setAttribute("user", user);

        // Forward to your JSP
        request.getRequestDispatcher("/dashboardStorekeeper.jsp").forward(request, response);
    }
}
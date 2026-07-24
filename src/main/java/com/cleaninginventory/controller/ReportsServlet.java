package com.cleaninginventory.controller;

import com.cleaninginventory.dao.ReportDAO;
import com.cleaninginventory.model.Report;
import com.cleaninginventory.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/reports")
public class ReportsServlet extends HttpServlet {

    private ReportDAO reportDAO;

    @Override
    public void init() {
        reportDAO = new ReportDAO();
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
        if (!"SUPERVISOR".equalsIgnoreCase(user.getRole()) && !"ADMIN".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/storekeeper/dashboard");
            return;
        }

        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            showReportsDashboard(request, response);
        } else {
            generateReport(request, response, type);
        }
    }

    private void showReportsDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int totalMaterials = reportDAO.getInventoryReport().size();
        int lowStockItems = reportDAO.getLowStockReport().size();
        int totalIssuances = reportDAO.getTotalIssuedCount();
        int openIssues = reportDAO.getOpenIssuesCount();

        request.setAttribute("totalMaterials", totalMaterials);
        request.setAttribute("lowStockItems", lowStockItems);
        request.setAttribute("totalIssuances", totalIssuances);
        request.setAttribute("openIssues", openIssues);

        request.getRequestDispatcher("/reportsDashboard.jsp").forward(request, response);
    }

    private void generateReport(HttpServletRequest request, HttpServletResponse response, String type)
            throws ServletException, IOException {

        request.setAttribute("reportType", type);

        switch (type) {
            case "inventory":
                List<Report.InventoryItem> inventoryItems = reportDAO.getInventoryReport();
                request.setAttribute("reportData", inventoryItems);
                request.setAttribute("reportTitle", "Inventory Report");
                request.setAttribute("reportDescription", "Complete inventory of all materials");
                break;

            case "lowstock":
                List<Report.InventoryItem> lowStockItems = reportDAO.getLowStockReport();
                request.setAttribute("reportData", lowStockItems);
                request.setAttribute("reportTitle", "Low Stock Report");
                request.setAttribute("reportDescription", "Materials that need to be reordered");
                break;

            case "issuance":
                List<Report.IssuanceItem> issuanceItems = reportDAO.getIssuanceHistory();
                request.setAttribute("reportData", issuanceItems);
                request.setAttribute("reportTitle", "Issuance History");
                request.setAttribute("reportDescription", "Complete history of all stock issuances");
                break;

            case "usage":
                List<Report.MaterialUsageItem> usageItems = reportDAO.getMaterialUsageReport();
                request.setAttribute("reportData", usageItems);
                request.setAttribute("reportTitle", "Material Usage Report");
                request.setAttribute("reportDescription", "Material consumption and remaining stock");
                break;

            default:
                showReportsDashboard(request, response);
                return;
        }

        request.getRequestDispatcher("/viewReport.jsp").forward(request, response);
    }
}

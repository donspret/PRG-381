package com.example.final_prg381.servlet;

import com.example.final_prg381.dao.StockIssuanceDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Serves the Reports page: inventory report, low-stock report,
 * issuance history and material usage report all in one place.
 */
@WebServlet(name = "reportsServlet", value = "/reports")
public class ReportsServlet extends HttpServlet {

    private final StockIssuanceDAO dao = new StockIssuanceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("inventoryReport", dao.getInventoryReport());
            request.setAttribute("lowStockReport", dao.getLowStockReport());
            request.setAttribute("issuanceHistory", dao.getIssuanceHistory());
            request.setAttribute("usageReport", dao.getMaterialUsageReport());
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Could not load reports: " + e.getMessage());
        }
        request.getRequestDispatcher("/reports.jsp").forward(request, response);
    }
}

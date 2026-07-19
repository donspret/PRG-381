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
 * Handles GET (show the issue-stock form + history) and POST
 * (process a new issuance) for /issueStock.
 */
@WebServlet(name = "stockIssuanceServlet", value = "/issueStock")
public class StockIssuanceServlet extends HttpServlet {

    private final StockIssuanceDAO dao = new StockIssuanceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("materials", dao.getAllMaterials());
            request.setAttribute("cleaners", dao.getAllCleaners());
            request.setAttribute("history", dao.getIssuanceHistory());
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Could not load data: " + e.getMessage());
        }
        request.getRequestDispatcher("/issueStock.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String materialIdParam = request.getParameter("materialId");
        String cleanerIdParam = request.getParameter("cleanerId");
        String quantityParam = request.getParameter("quantity");

        // Basic required-field validation before we touch the database
        if (isBlank(materialIdParam) || isBlank(cleanerIdParam) || isBlank(quantityParam)) {
            request.setAttribute("errorMessage", "All fields are required.");
            doGet(request, response);
            return;
        }

        try {
            int materialId = Integer.parseInt(materialIdParam);
            int cleanerId = Integer.parseInt(cleanerIdParam);
            int quantity = Integer.parseInt(quantityParam);

            // Swap this out for the logged-in user's username once
            // Member 1's session management is merged in.
            String issuedBy = (String) request.getSession().getAttribute("username");
            if (issuedBy == null) {
                issuedBy = "unknown";
            }

            dao.issueStock(materialId, cleanerId, quantity, issuedBy);
            request.setAttribute("successMessage", "Stock issued successfully.");

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Quantity must be a valid number.");
        } catch (StockIssuanceDAO.InsufficientStockException e) {
            request.setAttribute("errorMessage", e.getMessage());
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        doGet(request, response);
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}



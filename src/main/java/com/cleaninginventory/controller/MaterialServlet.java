package com.cleaninginventory.controller;

import com.cleaninginventory.dao.MaterialDAO;
import com.cleaninginventory.model.Material;
import com.cleaninginventory.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/materials")
public class MaterialServlet extends HttpServlet {
    private MaterialDAO materialDAO;

    @Override
    public void init() {
        System.out.println("✅ MaterialServlet initialized!");
        materialDAO = new MaterialDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        System.out.println("🔍 MaterialServlet action: " + action);

        switch (action) {
            case "list":
                listMaterials(request, response);
                break;
            case "new":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteMaterial(request, response);
                break;
            case "search":
                searchMaterials(request, response);
                break;
            case "lowstock":
                listLowStockMaterials(request, response);
                break;
            default:
                listMaterials(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addMaterial(request, response);
        } else if ("update".equals(action)) {
            updateMaterial(request, response);
        } else {
            response.sendRedirect("materials?action=list");
        }
    }

    private void listMaterials(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Material> materials = materialDAO.getAllMaterials();
        request.setAttribute("materials", materials);
        request.getRequestDispatcher("/materials/materialsList.jsp").forward(request, response);
    }

    private void listLowStockMaterials(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Material> materials = materialDAO.getLowStockMaterials();
        request.setAttribute("materials", materials);
        request.setAttribute("lowStockView", true);
        request.getRequestDispatcher("/materials/materialsList.jsp").forward(request, response);
    }

    private void searchMaterials(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Material> materials = materialDAO.searchMaterials(keyword);
        request.setAttribute("materials", materials);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("/materials/materialsList.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/materials/addMaterial.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Material material = materialDAO.getMaterialById(id);

            if (material == null) {
                request.setAttribute("error", "Material not found.");
                listMaterials(request, response);
                return;
            }

            request.setAttribute("material", material);
            request.getRequestDispatcher("/materials/editMaterial.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid material ID.");
            listMaterials(request, response);
        }
    }

    private void addMaterial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("materialName");
            String category = request.getParameter("category");
            String unit = request.getParameter("unit");
            String quantityStr = request.getParameter("quantityInStock");
            String thresholdStr = request.getParameter("lowStockThreshold");
            String priceStr = request.getParameter("unitPrice");
            String supplierStr = request.getParameter("supplierId");

            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("error", "Material name is required.");
                request.getRequestDispatcher("/materials/addMaterial.jsp").forward(request, response);
                return;
            }

            int quantity = quantityStr != null && !quantityStr.isEmpty() ? Integer.parseInt(quantityStr) : 0;
            int threshold = thresholdStr != null && !thresholdStr.isEmpty() ? Integer.parseInt(thresholdStr) : 5;
            BigDecimal price = priceStr != null && !priceStr.isEmpty() ? new BigDecimal(priceStr) : BigDecimal.ZERO;
            int supplierId = supplierStr != null && !supplierStr.isEmpty() ? Integer.parseInt(supplierStr) : 0;

            Material material = new Material();
            material.setMaterialName(name.trim());
            material.setCategory(category != null ? category.trim() : null);
            material.setUnit(unit != null ? unit.trim() : null);
            material.setQuantityInStock(quantity);
            material.setLowStockThreshold(threshold);
            material.setUnitPrice(price);
            material.setSupplierId(supplierId);

            boolean success = materialDAO.addMaterial(material);

            if (success) {
                request.setAttribute("success", "Material added successfully!");
            } else {
                request.setAttribute("error", "Failed to add material. Please try again.");
            }
            listMaterials(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format. Please check your input.");
            request.getRequestDispatcher("/materials/addMaterial.jsp").forward(request, response);
        }
    }

    private void updateMaterial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("materialId"));
            String name = request.getParameter("materialName");
            String category = request.getParameter("category");
            String unit = request.getParameter("unit");
            String quantityStr = request.getParameter("quantityInStock");
            String thresholdStr = request.getParameter("lowStockThreshold");
            String priceStr = request.getParameter("unitPrice");
            String supplierStr = request.getParameter("supplierId");

            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("error", "Material name is required.");
                request.setAttribute("material", materialDAO.getMaterialById(id));
                request.getRequestDispatcher("/materials/editMaterial.jsp").forward(request, response);
                return;
            }

            int quantity = quantityStr != null && !quantityStr.isEmpty() ? Integer.parseInt(quantityStr) : 0;
            int threshold = thresholdStr != null && !thresholdStr.isEmpty() ? Integer.parseInt(thresholdStr) : 5;
            BigDecimal price = priceStr != null && !priceStr.isEmpty() ? new BigDecimal(priceStr) : BigDecimal.ZERO;
            int supplierId = supplierStr != null && !supplierStr.isEmpty() ? Integer.parseInt(supplierStr) : 0;

            Material material = new Material();
            material.setMaterialId(id);
            material.setMaterialName(name.trim());
            material.setCategory(category != null ? category.trim() : null);
            material.setUnit(unit != null ? unit.trim() : null);
            material.setQuantityInStock(quantity);
            material.setLowStockThreshold(threshold);
            material.setUnitPrice(price);
            material.setSupplierId(supplierId);

            boolean success = materialDAO.updateMaterial(material);

            if (success) {
                request.setAttribute("success", "Material updated successfully!");
            } else {
                request.setAttribute("error", "Failed to update material. Please try again.");
            }
            listMaterials(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format. Please check your input.");
            try {
                int id = Integer.parseInt(request.getParameter("materialId"));
                request.setAttribute("material", materialDAO.getMaterialById(id));
            } catch (Exception ex) {

            }
            request.getRequestDispatcher("/materials/editMaterial.jsp").forward(request, response);
        }
    }

    private void deleteMaterial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            Material material = materialDAO.getMaterialById(id);
            if (material == null) {
                request.setAttribute("error", "Material not found.");
                listMaterials(request, response);
                return;
            }

            boolean success = materialDAO.deleteMaterial(id);

            if (success) {
                request.setAttribute("success", "Material deleted successfully!");
            } else {
                request.setAttribute("error", "Failed to delete material. Please try again.");
            }
            listMaterials(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid material ID.");
            listMaterials(request, response);
        }
    }
}
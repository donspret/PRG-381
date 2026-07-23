package com.example.final_prg381.servlet;

import com.example.final_prg381.dao.materialDAO;
import com.example.final_prg381.model.Material;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


    @WebServlet("/materials")
    public class MaterialServlet extends HttpServlet {

        private final materialDAO materialDAO = new materialDAO();

        // Handles  GET requests
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            String action = request.getParameter("action");
            if (action == null) {
                action = "list";
            }

            switch (action) {
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
                    showLowStock(request, response);
                    break;
                default:
                    listMaterials(request, response);
                    break;
            }
        }

        // Handles POST requests
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            String action = request.getParameter("action");
            if (action == null) action = "";

            switch (action) {
                case "insert":
                    insertMaterial(request, response);
                    break;
                case "update":
                    updateMaterial(request, response);
                    break;
                default:
                    listMaterials(request, response);
                    break;
            }
        }

        // LIST
        private void listMaterials(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            List<Material> materials = materialDAO.getAllMaterials();
            request.setAttribute("materials", materials);
            request.getRequestDispatcher("materials.jsp").forward(request, response);
        }

        // show add form
        private void showAddForm(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            request.getRequestDispatcher("materialForm.jsp").forward(request, response);
        }

        // show edit form
        private void showEditForm(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            int id = Integer.parseInt(request.getParameter("id"));
            Material material = materialDAO.getMaterialById(id);
            request.setAttribute("material", material);
            request.getRequestDispatcher("materialForm.jsp").forward(request, response);
        }

        // insert
        private void insertMaterial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Material m = this.buildMaterialFromRequest(request);
            String validationError = this.materialDAO.validate(m);
            if (validationError != null) {
                request.setAttribute("errorMessage", validationError);
                request.setAttribute("material", m);
                request.getRequestDispatcher("materialForm.jsp").forward(request, response);
                return;
            }
            boolean success = this.materialDAO.addMaterial(m);
            if (!success) {
                request.setAttribute("errorMessage", "Failed to save material. Please check the Supplier ID exists and try again.");
                request.setAttribute("material", m);
                request.getRequestDispatcher("materialForm.jsp").forward(request, response);
                return;
            }
            response.sendRedirect("materials");
        }

        // update
        private void updateMaterial(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            Material m = buildMaterialFromRequest(request);
            m.setMaterialId(Integer.parseInt(request.getParameter("materialId")));

            String validationError = materialDAO.validate(m);
            if (validationError != null) {
                request.setAttribute("errorMessage", validationError);
                request.setAttribute("material", m);
                request.getRequestDispatcher("materialForm.jsp").forward(request, response);
                return;
            }

            materialDAO.updateMaterial(m);
            response.sendRedirect("materials");
        }

        // delete
        private void deleteMaterial(HttpServletRequest request, HttpServletResponse response)
                throws IOException {
            int id = Integer.parseInt(request.getParameter("id"));
            materialDAO.deleteMaterial(id);
            response.sendRedirect("materials");
        }

        // search/filter
        private void searchMaterials(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String keyword = request.getParameter("keyword");
            String category = request.getParameter("category");

            List<Material> materials = materialDAO.searchMaterials(keyword, category);
            request.setAttribute("materials", materials);
            request.setAttribute("keyword", keyword);
            request.setAttribute("category", category);
            request.getRequestDispatcher("materials.jsp").forward(request, response);
        }

        // low stock view
        private void showLowStock(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            List<Material> materials = materialDAO.getLowStockMaterials();
            request.setAttribute("materials", materials);
            request.setAttribute("lowStockView", true);
            request.getRequestDispatcher("materials.jsp").forward(request, response);
        }

        //Helper: reads form fields out of the request into a Material object
        private Material buildMaterialFromRequest(HttpServletRequest request) {
            Material m = new Material();
            m.setMaterialName(request.getParameter("materialName"));
            m.setCategory(request.getParameter("category"));
            m.setUnit(request.getParameter("unit"));

            try {
                m.setQuantityInStock(Integer.parseInt(request.getParameter("quantityInStock")));
            } catch (NumberFormatException e) {
                m.setQuantityInStock(-1);
            }

            try {
                m.setLowStockThreshold(Integer.parseInt(request.getParameter("lowStockThreshold")));
            } catch (NumberFormatException e) {
                m.setLowStockThreshold(-1);
            }

            try {
                m.setUnitPrice(new BigDecimal(request.getParameter("unitPrice")));
            } catch (Exception e) {
                m.setUnitPrice(BigDecimal.ZERO);
            }

            try {
                m.setSupplierId(Integer.parseInt(request.getParameter("supplierId")));
            } catch (NumberFormatException e) {
                m.setSupplierId(-1);
            }

            return m;
        }
    }


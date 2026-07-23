package com.cleaninginventory.controller;

import com.cleaninginventory.dao.SupplierDAO;
import com.cleaninginventory.model.Supplier;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/SupplierServlet")
public class SupplierServlet extends HttpServlet {
    private SupplierDAO supplierDAO;

    @Override
    public void init() {
        supplierDAO = new SupplierDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listSuppliers(request, response);
                break;
            case "search":
                searchSuppliers(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteSupplier(request, response);
                break;
            default:
                listSuppliers(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("SupplierServlet?action=list");
            return;
        }

        switch (action) {
            case "add":
                addSupplier(request, response);
                break;
            case "update":
                updateSupplier(request, response);
                break;
            default:
                response.sendRedirect("SupplierServlet?action=list");
        }
    }

    private void listSuppliers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Supplier> suppliers = supplierDAO.getAllSuppliers();
        request.setAttribute("suppliers", suppliers);
        request.getRequestDispatcher("/suppliers/supplierList.jsp").forward(request, response);
    }

    private void searchSuppliers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Supplier> suppliers = supplierDAO.searchSuppliers(keyword);
        request.setAttribute("suppliers", suppliers);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("/suppliers/supplierList.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/suppliers/addSupplier.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int supplierId = Integer.parseInt(request.getParameter("id"));
        Supplier supplier = supplierDAO.getSupplierById(supplierId);
        request.setAttribute("supplier", supplier);
        request.getRequestDispatcher("/suppliers/editSupplier.jsp").forward(request, response);
    }

    private void addSupplier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String companyName = request.getParameter("companyName");
        String contactPerson = request.getParameter("contactPerson");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String postalCode = request.getParameter("postalCode");
        String country = request.getParameter("country");
        String status = request.getParameter("status");
        String notes = request.getParameter("notes");

        if (companyName == null || companyName.trim().isEmpty() ||
                contactPerson == null || contactPerson.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty()) {

            request.setAttribute("error", "Company Name, Contact Person, Email and Phone are required.");
            request.getRequestDispatcher("/suppliers/addSupplier.jsp").forward(request, response);
            return;
        }

        if (supplierDAO.emailExists(email)) {
            request.setAttribute("error", "Email already exists. Please use a different email.");
            request.getRequestDispatcher("/suppliers/addSupplier.jsp").forward(request, response);
            return;
        }

        Supplier supplier = new Supplier();
        supplier.setCompanyName(companyName.trim());
        supplier.setContactPerson(contactPerson.trim());
        supplier.setEmail(email.trim());
        supplier.setPhone(phone.trim());
        supplier.setAddress(address != null ? address.trim() : "");
        supplier.setCity(city != null ? city.trim() : "");
        supplier.setPostalCode(postalCode != null ? postalCode.trim() : "");
        supplier.setCountry(country != null ? country.trim() : "");
        supplier.setStatus(status != null ? status : "Active");
        supplier.setNotes(notes != null ? notes.trim() : "");

        boolean success = supplierDAO.addSupplier(supplier);

        if (success) {
            request.setAttribute("success", "Supplier added successfully!");
        } else {
            request.setAttribute("error", "Failed to add supplier. Please try again.");
        }

        listSuppliers(request, response);
    }

    private void updateSupplier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int supplierId = Integer.parseInt(request.getParameter("supplierId"));
        String companyName = request.getParameter("companyName");
        String contactPerson = request.getParameter("contactPerson");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String postalCode = request.getParameter("postalCode");
        String country = request.getParameter("country");
        String status = request.getParameter("status");
        String notes = request.getParameter("notes");

        if (companyName == null || companyName.trim().isEmpty() ||
                contactPerson == null || contactPerson.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty()) {

            request.setAttribute("error", "Company Name, Contact Person, Email and Phone are required.");
            request.setAttribute("supplier", supplierDAO.getSupplierById(supplierId));
            request.getRequestDispatcher("/suppliers/editSupplier.jsp").forward(request, response);
            return;
        }

        Supplier supplier = new Supplier();
        supplier.setSupplierId(supplierId);
        supplier.setCompanyName(companyName.trim());
        supplier.setContactPerson(contactPerson.trim());
        supplier.setEmail(email.trim());
        supplier.setPhone(phone.trim());
        supplier.setAddress(address != null ? address.trim() : "");
        supplier.setCity(city != null ? city.trim() : "");
        supplier.setPostalCode(postalCode != null ? postalCode.trim() : "");
        supplier.setCountry(country != null ? country.trim() : "");
        supplier.setStatus(status != null ? status : "Active");
        supplier.setNotes(notes != null ? notes.trim() : "");

        boolean success = supplierDAO.updateSupplier(supplier);

        if (success) {
            request.setAttribute("success", "Supplier updated successfully!");
        } else {
            request.setAttribute("error", "Failed to update supplier. Please try again.");
        }

        listSuppliers(request, response);
    }

    private void deleteSupplier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int supplierId = Integer.parseInt(request.getParameter("id"));
        boolean success = supplierDAO.deleteSupplier(supplierId);

        if (success) {
            request.setAttribute("success", "Supplier deleted successfully!");
        } else {
            request.setAttribute("error", "Failed to delete supplier. Please try again.");
        }

        listSuppliers(request, response);
    }
}
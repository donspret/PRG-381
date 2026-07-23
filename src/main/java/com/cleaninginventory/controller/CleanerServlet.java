package com.cleaninginventory.controller;

import com.cleaninginventory.dao.CleanerDAO;
import com.cleaninginventory.model.Cleaner;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/CleanerServlet")
public class CleanerServlet extends HttpServlet {
    private CleanerDAO cleanerDAO;

    @Override
    public void init() {
        cleanerDAO = new CleanerDAO();
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
                listCleaners(request, response);
                break;
            case "search":
                searchCleaners(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteCleaner(request, response);
                break;
            default:
                listCleaners(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("CleanerServlet?action=list");
            return;
        }

        switch (action) {
            case "add":
                addCleaner(request, response);
                break;
            case "update":
                updateCleaner(request, response);
                break;
            default:
                response.sendRedirect("CleanerServlet?action=list");
        }
    }

    private void listCleaners(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cleaner> cleaners = cleanerDAO.getAllCleaners();
        request.setAttribute("cleaners", cleaners);
        request.getRequestDispatcher("/cleaners/cleanerList.jsp").forward(request, response);
    }

    private void searchCleaners(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Cleaner> cleaners = cleanerDAO.searchCleaners(keyword);
        request.setAttribute("cleaners", cleaners);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("/cleaners/cleanerList.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> departments = cleanerDAO.getDepartments();
        request.setAttribute("departments", departments);
        request.getRequestDispatcher("/cleaners/addCleaner.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int cleanerId = Integer.parseInt(request.getParameter("id"));
        Cleaner cleaner = cleanerDAO.getCleanerById(cleanerId);
        List<String> departments = cleanerDAO.getDepartments();
        request.setAttribute("cleaner", cleaner);
        request.setAttribute("departments", departments);
        request.getRequestDispatcher("/cleaners/editCleaner.jsp").forward(request, response);
    }

    private void addCleaner(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String department = request.getParameter("department");
        String status = request.getParameter("status");
        String shift = request.getParameter("shift");
        String notes = request.getParameter("notes");

        if (firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty()) {

            request.setAttribute("error", "First Name, Last Name, Email and Phone are required.");
            request.getRequestDispatcher("/cleaners/addCleaner.jsp").forward(request, response);
            return;
        }

        if (cleanerDAO.emailExists(email)) {
            request.setAttribute("error", "Email already exists. Please use a different email.");
            request.getRequestDispatcher("/cleaners/addCleaner.jsp").forward(request, response);
            return;
        }

        Cleaner cleaner = new Cleaner();
        cleaner.setFirstName(firstName.trim());
        cleaner.setLastName(lastName.trim());
        cleaner.setEmail(email.trim());
        cleaner.setPhone(phone.trim());
        cleaner.setAddress(address != null ? address.trim() : "");
        cleaner.setDepartment(department != null ? department.trim() : "");
        cleaner.setStatus(status != null ? status : "Active");
        cleaner.setShift(shift != null ? shift.trim() : "Morning");
        cleaner.setNotes(notes != null ? notes.trim() : "");

        boolean success = cleanerDAO.addCleaner(cleaner);

        if (success) {
            request.setAttribute("success", "Cleaner added successfully!");
        } else {
            request.setAttribute("error", "Failed to add cleaner. Please try again.");
        }

        listCleaners(request, response);
    }

    private void updateCleaner(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int cleanerId = Integer.parseInt(request.getParameter("cleanerId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String department = request.getParameter("department");
        String status = request.getParameter("status");
        String shift = request.getParameter("shift");
        String notes = request.getParameter("notes");

        if (firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty()) {

            request.setAttribute("error", "First Name, Last Name, Email and Phone are required.");
            request.setAttribute("cleaner", cleanerDAO.getCleanerById(cleanerId));
            request.getRequestDispatcher("/cleaners/editCleaner.jsp").forward(request, response);
            return;
        }

        Cleaner cleaner = new Cleaner();
        cleaner.setCleanerId(cleanerId);
        cleaner.setFirstName(firstName.trim());
        cleaner.setLastName(lastName.trim());
        cleaner.setEmail(email.trim());
        cleaner.setPhone(phone.trim());
        cleaner.setAddress(address != null ? address.trim() : "");
        cleaner.setDepartment(department != null ? department.trim() : "");
        cleaner.setStatus(status != null ? status : "Active");
        cleaner.setShift(shift != null ? shift.trim() : "Morning");
        cleaner.setNotes(notes != null ? notes.trim() : "");

        boolean success = cleanerDAO.updateCleaner(cleaner);

        if (success) {
            request.setAttribute("success", "Cleaner updated successfully!");
        } else {
            request.setAttribute("error", "Failed to update cleaner. Please try again.");
        }

        listCleaners(request, response);
    }

    private void deleteCleaner(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int cleanerId = Integer.parseInt(request.getParameter("id"));
        boolean success = cleanerDAO.deleteCleaner(cleanerId);

        if (success) {
            request.setAttribute("success", "Cleaner deleted successfully!");
        } else {
            request.setAttribute("error", "Failed to delete cleaner. Please try again.");
        }

        listCleaners(request, response);
    }
}
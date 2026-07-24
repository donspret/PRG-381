package com.cleaninginventory.controller;

import com.cleaninginventory.dao.*;
import com.cleaninginventory.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/issues")
public class IssueServlet extends HttpServlet {

    private IssueDAO issueDAO;
    private MaterialDAO materialDAO;
    private SupplierDAO supplierDAO;
    private UserDAO userDAO;

    @Override
    public void init() {
        issueDAO = new IssueDAO();
        materialDAO = new MaterialDAO();
        supplierDAO = new SupplierDAO();
        userDAO = new UserDAO();
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
        String action = request.getParameter("action");
        if (action == null) action = "list";

        if ("STOREKEEPER".equalsIgnoreCase(user.getRole())) {
            if ("new".equals(action)) {
                showCreateForm(request, response);
            } else if ("create".equals(action)) {

            } else {

                viewStorekeeperIssues(request, response);
            }
        } else {

            switch (action) {
                case "list":
                    viewAllIssues(request, response);
                    break;
                case "view":
                    viewIssueDetail(request, response);
                    break;
                case "resolve":
                    showResolveForm(request, response);
                    break;
                case "open":
                    viewOpenIssues(request, response);
                    break;
                case "inprogress":
                    viewInProgressIssues(request, response);
                    break;
                case "resolved":
                    viewResolvedIssues(request, response);
                    break;
                default:
                    viewAllIssues(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if ("create".equals(action)) {
            createIssue(request, response);
        } else if ("resolve".equals(action)) {
            resolveIssue(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/issues?action=list");
        }
    }


    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Material> materials = materialDAO.getAllMaterials();
        List<Supplier> suppliers = supplierDAO.getAllSuppliers();
        request.setAttribute("materials", materials);
        request.setAttribute("suppliers", suppliers);
        request.setAttribute("issueTypes", getIssueTypes());
        request.setAttribute("severities", getSeverities());
        request.getRequestDispatcher("/createIssue.jsp").forward(request, response);
    }


    private void createIssue(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        String issueType = request.getParameter("issueType");
        String description = request.getParameter("description");
        String severity = request.getParameter("severity");
        String materialIdStr = request.getParameter("materialId");
        String supplierIdStr = request.getParameter("supplierId");


        if (issueType == null || issueType.trim().isEmpty() ||
                description == null || description.trim().isEmpty()) {

            request.setAttribute("error", "Issue type and description are required.");
            showCreateForm(request, response);
            return;
        }

        int materialId = materialIdStr != null && !materialIdStr.isEmpty() ? Integer.parseInt(materialIdStr) : 0;
        int supplierId = supplierIdStr != null && !supplierIdStr.isEmpty() ? Integer.parseInt(supplierIdStr) : 0;

        Issue issue = new Issue();
        issue.setIssueType(issueType);
        issue.setDescription(description.trim());
        issue.setSeverity(severity != null ? severity : Issue.SEVERITY_MEDIUM);
        issue.setMaterialId(materialId);
        issue.setSupplierId(supplierId);
        issue.setStorekeeperId(user.getUserId());

        boolean success = issueDAO.addIssue(issue);

        if (success) {
            request.setAttribute("success", "Issue reported successfully! Your issue ID is: " + issue.getIssueId());
            viewStorekeeperIssues(request, response);
        } else {
            request.setAttribute("error", "Failed to report issue. Please try again.");
            showCreateForm(request, response);
        }
    }

    private void viewStorekeeperIssues(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        List<Issue> issues = issueDAO.getIssuesByStorekeeper(user.getUserId());
        request.setAttribute("issues", issues);
        request.setAttribute("isStorekeeper", true);
        request.getRequestDispatcher("/viewIssues.jsp").forward(request, response);
    }

    private void viewAllIssues(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Issue> issues = issueDAO.getAllIssues();
        request.setAttribute("issues", issues);
        request.setAttribute("isStorekeeper", false);
        request.getRequestDispatcher("/viewIssues.jsp").forward(request, response);
    }

    private void viewOpenIssues(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Issue> issues = issueDAO.getIssuesByStatus(Issue.STATUS_OPEN);
        request.setAttribute("issues", issues);
        request.setAttribute("filter", "Open");
        request.setAttribute("isStorekeeper", false);
        request.getRequestDispatcher("/viewIssues.jsp").forward(request, response);
    }

    private void viewInProgressIssues(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Issue> issues = issueDAO.getIssuesByStatus(Issue.STATUS_IN_PROGRESS);
        request.setAttribute("issues", issues);
        request.setAttribute("filter", "In Progress");
        request.setAttribute("isStorekeeper", false);
        request.getRequestDispatcher("/viewIssues.jsp").forward(request, response);
    }

    private void viewResolvedIssues(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Issue> issues = issueDAO.getIssuesByStatus(Issue.STATUS_RESOLVED);
        request.setAttribute("issues", issues);
        request.setAttribute("filter", "Resolved");
        request.setAttribute("isStorekeeper", false);
        request.getRequestDispatcher("/viewIssues.jsp").forward(request, response);
    }

    private void viewIssueDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int issueId = Integer.parseInt(request.getParameter("id"));
        Issue issue = issueDAO.getIssueById(issueId);
        request.setAttribute("issue", issue);
        request.getRequestDispatcher("/viewIssueDetail.jsp").forward(request, response);
    }

    private void showResolveForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int issueId = Integer.parseInt(request.getParameter("id"));
        Issue issue = issueDAO.getIssueById(issueId);
        request.setAttribute("issue", issue);
        request.getRequestDispatcher("/resolveIssue.jsp").forward(request, response);
    }

    private void resolveIssue(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        int issueId = Integer.parseInt(request.getParameter("issueId"));
        String resolution = request.getParameter("resolution");

        if (resolution == null || resolution.trim().isEmpty()) {
            request.setAttribute("error", "Resolution is required.");
            Issue issue = issueDAO.getIssueById(issueId);
            request.setAttribute("issue", issue);
            request.getRequestDispatcher("/resolveIssue.jsp").forward(request, response);
            return;
        }

        boolean success = issueDAO.resolveIssue(issueId, resolution.trim(), user.getUserId());

        if (success) {
            request.setAttribute("success", "Issue resolved successfully!");
            viewAllIssues(request, response);
        } else {
            request.setAttribute("error", "Failed to resolve issue. Please try again.");
            viewAllIssues(request, response);
        }
    }

    private String[] getIssueTypes() {
        return new String[]{
                Issue.TYPE_DEFECTIVE,
                Issue.TYPE_TECHNICAL,
                Issue.TYPE_DELAYS,
                Issue.TYPE_SUPPLIER,
                Issue.TYPE_SHORTAGE,
                Issue.TYPE_CONFLICT
        };
    }

    private String[] getSeverities() {
        return new String[]{
                Issue.SEVERITY_LOW,
                Issue.SEVERITY_MEDIUM,
                Issue.SEVERITY_HIGH,
                Issue.SEVERITY_CRITICAL
        };
    }
}

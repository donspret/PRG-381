<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleaninginventory.model.User" %>
<%@ page import="com.cleaninginventory.model.Issue" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    List<Issue> issues = (List<Issue>) request.getAttribute("issues");
    Boolean isStorekeeper = (Boolean) request.getAttribute("isStorekeeper");
    String filter = (String) request.getAttribute("filter");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Issues - Cleaning Inventory System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #eee; padding-bottom: 15px; margin-bottom: 20px; }
        .header h2 { color: #2c3e50; margin: 0; }
        .header a { color: white; background: #e74c3c; padding: 5px 15px; text-decoration: none; border-radius: 4px; font-size: 14px; }
        .header a:hover { background: #c0392b; }
        .header .new-btn { background: #27ae60; }
        .header .new-btn:hover { background: #219a52; }
        .header .dashboard-btn { background: #3498db; }
        .header .dashboard-btn:hover { background: #2980b9; }
        .filter-bar { display: flex; gap: 10px; margin-bottom: 20px; flex-wrap: wrap; align-items: center; }
        .filter-bar a { padding: 5px 12px; background: #ecf0f1; color: #2c3e50; text-decoration: none; border-radius: 4px; }
        .filter-bar a:hover { background: #3498db; color: white; }
        .filter-bar a.active { background: #3498db; color: white; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background: #2c3e50; color: white; }
        tr:hover { background: #f5f5f5; }
        .severity-critical { color: darkred; font-weight: bold; }
        .severity-high { color: red; font-weight: bold; }
        .severity-medium { color: orange; font-weight: bold; }
        .severity-low { color: green; }
        .status-open { color: red; font-weight: bold; }
        .status-inprogress { color: orange; font-weight: bold; }
        .status-resolved { color: green; font-weight: bold; }
        .status-closed { color: gray; }
        .btn-view { background: #3498db; color: white; padding: 3px 10px; text-decoration: none; border-radius: 4px; font-size: 12px; }
        .btn-view:hover { background: #2980b9; }
        .btn-resolve { background: #27ae60; color: white; padding: 3px 10px; text-decoration: none; border-radius: 4px; font-size: 12px; }
        .btn-resolve:hover { background: #219a52; }
        .no-data { text-align: center; color: #7f8c8d; padding: 20px; }
        .alert { padding: 12px 15px; margin-bottom: 20px; border-radius: 4px; }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .footer { text-align: center; color: #7f8c8d; font-size: 12px; margin-top: 30px; padding-top: 15px; border-top: 1px solid #ecf0f1; }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h2>Issues</h2>
        <div>
            <a href="${pageContext.request.contextPath}/issues?action=new" class="new-btn">New Issue</a>
            <%
                if (isStorekeeper != null && isStorekeeper) {
            %>
                <a href="${pageContext.request.contextPath}/storekeeper/dashboard" class="dashboard-btn">Dashboard</a>
            <%
                } else {
            %>
                <a href="${pageContext.request.contextPath}/DashboardServlet" class="dashboard-btn">Dashboard</a>
            <%
                }
            %>
            <a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
        </div>
    </div>

    <%
        String error = (String) request.getAttribute("error");
        String success = (String) request.getAttribute("success");
        if (error != null) {
    %>
        <div class="alert alert-error"><%= error %></div>
    <%
        }
        if (success != null) {
    %>
        <div class="alert alert-success"><%= success %></div>
    <%
        }
    %>

    <% if (isStorekeeper == null || !isStorekeeper) { %>
        <div class="filter-bar">
            <a href="${pageContext.request.contextPath}/issues?action=list" class="<%= filter == null ? "active" : "" %>">All</a>
            <a href="${pageContext.request.contextPath}/issues?action=open" class="<%= "Open".equals(filter) ? "active" : "" %>">Open</a>
            <a href="${pageContext.request.contextPath}/issues?action=inprogress" class="<%= "In Progress".equals(filter) ? "active" : "" %>">In Progress</a>
            <a href="${pageContext.request.contextPath}/issues?action=resolved" class="<%= "Resolved".equals(filter) ? "active" : "" %>">Resolved</a>
        </div>
    <% } %>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Issue Type</th>
                <th>Severity</th>
                <th>Status</th>
                <th>Submitted By</th>
                <th>Material</th>
                <th>Supplier</th>
                <th>Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (issues != null && !issues.isEmpty()) {
                    for (Issue issue : issues) {
            %>
            <tr>
                <td><%= issue.getIssueId() %></td>
                <td><%= issue.getIssueType() %></td>
                <td class="severity-<%= issue.getSeverity().toLowerCase() %>"><%= issue.getSeverity() %></td>
                <td class="status-<%= issue.getStatus().toLowerCase().replace(" ", "") %>"><%= issue.getStatus() %></td>
                <td><%= issue.getStorekeeperName() %></td>
                <td><%= issue.getMaterialName() != null ? issue.getMaterialName() : "N/A" %></td>
                <td><%= issue.getSupplierName() != null ? issue.getSupplierName() : "N/A" %></td>
                <td><%= issue.getCreatedAt() %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/issues?action=view&id=<%= issue.getIssueId() %>" class="btn-view">View</a>
                    <%
                        if (isStorekeeper == null || !isStorekeeper) {
                            if (!Issue.STATUS_RESOLVED.equals(issue.getStatus())) {
                    %>
                        <a href="${pageContext.request.contextPath}/issues?action=resolve&id=<%= issue.getIssueId() %>" class="btn-resolve">Resolve</a>
                    <%
                            }
                        }
                    %>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="9" class="no-data">No issues found.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <div class="footer">
        <p>Cleaning Inventory System - University Management &copy; 2026</p>
    </div>
</div>

</body>
</html>
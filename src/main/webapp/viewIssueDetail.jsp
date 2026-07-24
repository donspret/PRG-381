<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleaninginventory.model.User" %>
<%@ page import="com.cleaninginventory.model.Issue" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    Issue issue = (Issue) request.getAttribute("issue");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Issue Detail - Cleaning Inventory System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #eee; padding-bottom: 15px; margin-bottom: 20px; }
        .header h2 { color: #2c3e50; margin: 0; }
        .header a { color: white; background: #e74c3c; padding: 5px 15px; text-decoration: none; border-radius: 4px; font-size: 14px; }
        .header a:hover { background: #c0392b; }
        .header .back-btn { background: #3498db; }
        .header .back-btn:hover { background: #2980b9; }
        .detail-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; }
        .detail-item { padding: 10px; background: #f8f9fa; border-radius: 4px; }
        .detail-item label { font-weight: bold; display: block; color: #7f8c8d; font-size: 12px; text-transform: uppercase; }
        .detail-item .value { font-size: 16px; color: #2c3e50; margin-top: 5px; }
        .severity-critical { color: darkred; font-weight: bold; }
        .severity-high { color: red; font-weight: bold; }
        .severity-medium { color: orange; font-weight: bold; }
        .severity-low { color: green; }
        .status-open { color: red; font-weight: bold; }
        .status-inprogress { color: orange; font-weight: bold; }
        .status-resolved { color: green; font-weight: bold; }
        .status-closed { color: gray; }
        .full-width { grid-column: 1 / -1; }
        .footer { text-align: center; color: #7f8c8d; font-size: 12px; margin-top: 30px; padding-top: 15px; border-top: 1px solid #ecf0f1; }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h2>📋 Issue #<%= issue.getIssueId() %></h2>
        <div>
            <a href="${pageContext.request.contextPath}/issues" class="back-btn">← Back to Issues</a>
            <%
                if ("STOREKEEPER".equalsIgnoreCase(user.getRole())) {
            %>
                <a href="${pageContext.request.contextPath}/storekeeper/dashboard">Dashboard</a>
            <%
                } else {
            %>
                <a href="${pageContext.request.contextPath}/DashboardServlet">Dashboard</a>
            <%
                }
            %>
            <a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
        </div>
    </div>

    <div class="detail-grid">
        <div class="detail-item">
            <label>Issue Type</label>
            <div class="value"><%= issue.getIssueType() %></div>
        </div>
        <div class="detail-item">
            <label>Severity</label>
            <div class="value severity-<%= issue.getSeverity().toLowerCase() %>"><%= issue.getSeverity() %></div>
        </div>
        <div class="detail-item">
            <label>Status</label>
            <div class="value status-<%= issue.getStatus().toLowerCase().replace(" ", "") %>"><%= issue.getStatus() %></div>
        </div>
        <div class="detail-item">
            <label>Submitted By</label>
            <div class="value"><%= issue.getStorekeeperName() %></div>
        </div>
        <div class="detail-item">
            <label>Related Material</label>
            <div class="value"><%= issue.getMaterialName() != null ? issue.getMaterialName() : "None" %></div>
        </div>
        <div class="detail-item">
            <label>Related Supplier</label>
            <div class="value"><%= issue.getSupplierName() != null ? issue.getSupplierName() : "None" %></div>
        </div>
        <div class="detail-item">
            <label>Created Date</label>
            <div class="value"><%= issue.getCreatedAt() %></div>
        </div>
        <div class="detail-item">
            <label>Resolved Date</label>
            <div class="value"><%= issue.getResolvedAt() != null ? issue.getResolvedAt() : "Not yet resolved" %></div>
        </div>
        <div class="detail-item full-width">
            <label>Description</label>
            <div class="value"><%= issue.getDescription() %></div>
        </div>
        <%
            if (issue.getResolution() != null && !issue.getResolution().isEmpty()) {
        %>
            <div class="detail-item full-width">
                <label>Resolution</label>
                <div class="value"><%= issue.getResolution() %></div>
            </div>
            <div class="detail-item full-width">
                <label>Resolved By</label>
                <div class="value"><%= issue.getResolvedByName() != null ? issue.getResolvedByName() : "Unknown" %></div>
            </div>
        <%
            }
        %>
    </div>

    <div class="footer">
        <p>Cleaning Inventory System - University Management &copy; 2026</p>
    </div>
</div>

</body>
</html>
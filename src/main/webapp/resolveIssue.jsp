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
    if (issue == null) {
        response.sendRedirect(request.getContextPath() + "/issues?action=list");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Resolve Issue - Cleaning Inventory System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { max-width: 700px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #eee; padding-bottom: 15px; margin-bottom: 20px; }
        .header h2 { color: #2c3e50; margin: 0; }
        .header a { color: white; background: #e74c3c; padding: 5px 15px; text-decoration: none; border-radius: 4px; font-size: 14px; }
        .header a:hover { background: #c0392b; }
        .header .back-btn { background: #3498db; }
        .header .back-btn:hover { background: #2980b9; }
        .form-group { margin-bottom: 15px; }
        label { display: block; font-weight: bold; margin-bottom: 5px; color: #333; }
        textarea { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; font-size: 14px; font-family: Arial; }
        .required { color: red; }
        .btn { padding: 10px 20px; background-color: #27ae60; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 14px; font-weight: bold; }
        .btn:hover { background-color: #219a52; }
        .btn-cancel { background-color: #95a5a6; margin-left: 10px; }
        .btn-cancel:hover { background-color: #7f8c8d; }
        .alert { padding: 12px 15px; margin-bottom: 20px; border-radius: 4px; }
        .alert-error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .info-box { background: #ecf0f1; padding: 15px; border-radius: 4px; margin-bottom: 20px; }
        .info-box p { margin: 5px 0; }
        .footer { text-align: center; color: #7f8c8d; font-size: 12px; margin-top: 30px; padding-top: 15px; border-top: 1px solid #ecf0f1; }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h2>Resolve Issue #<%= issue.getIssueId() %></h2>
        <div>
            <a href="${pageContext.request.contextPath}/issues" class="back-btn">← Back to Issues</a>
            <a href="${pageContext.request.contextPath}/DashboardServlet">Dashboard</a>
            <a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
        </div>
    </div>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <div class="alert alert-error"><%= error %></div>
    <%
        }
    %>

    <div class="info-box">
        <p><strong>Issue Type:</strong> <%= issue.getIssueType() %></p>
        <p><strong>Severity:</strong> <%= issue.getSeverity() %></p>
        <p><strong>Description:</strong> <%= issue.getDescription() %></p>
        <p><strong>Submitted By:</strong> <%= issue.getStorekeeperName() %></p>
    </div>

    <form action="${pageContext.request.contextPath}/issues" method="post">
        <input type="hidden" name="action" value="resolve">
        <input type="hidden" name="issueId" value="<%= issue.getIssueId() %>">

        <div class="form-group">
            <label for="resolution">Resolution <span class="required">*</span></label>
            <textarea id="resolution" name="resolution" rows="4" placeholder="Describe how this issue was resolved..." required></textarea>
        </div>

        <button type="submit" class="btn">Resolve Issue</button>
        <a href="${pageContext.request.contextPath}/issues" class="btn btn-cancel">Cancel</a>
    </form>

    <div class="footer">
        <p>Cleaning Inventory System - University Management &copy; 2026</p>
    </div>
</div>

</body>
</html>
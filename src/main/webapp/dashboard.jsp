<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleaninginventory.model.User" %>
<%@ page import="com.cleaninginventory.model.DashboardStats" %>

<%
    User user = (User) session.getAttribute("user");
    DashboardStats stats = (DashboardStats) request.getAttribute("stats");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>

<h2>Cleaning Inventory System</h2>

<hr>

<p>
    <b>User:</b> <%= user.getFirstName() %> <%= user.getLastName() %>
    | <b>Role:</b> <%= user.getRole() %>
    | <a href="LogoutServlet">Logout</a>
</p>

<hr>

<p><b>Welcome back, <%= user.getFirstName() %>!</b></p>

<hr>

<h3>Statistics</h3>
<ul>
    <li>Total Materials: <%= stats != null ? stats.getTotalMaterials() : 0 %></li>
    <li>Low Stock Items: <%= stats != null ? stats.getLowStockItems() : 0 %></li>
    <li>Total Cleaners: <%= stats != null ? stats.getTotalCleaners() : 0 %></li>
    <li>Recent Issuances: <%= stats != null ? stats.getRecentIssuances() : 0 %></li>
    <li>Total Users: <%= stats != null ? stats.getTotalUsers() : 0 %></li>
</ul>

<hr>

<h3>Quick Actions</h3>
<ul>
    <li><a href="materials?action=new">Add Material</a></li>
    <li><a href="materials?action=lowstock">View Low Stock</a></li>
    <li><a href="issuance?action=new">New Issuance</a></li>
    <li><a href="reports">Generate Reports</a></li>
</ul>

<hr>

<p><small>Cleaning Inventory System - University Management</small></p>

</body>
</html>
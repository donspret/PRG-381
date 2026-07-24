<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleaninginventory.model.User" %>
<%@ page import="com.cleaninginventory.model.DashboardStats" %>

<%
    User user = (User) session.getAttribute("user");
    DashboardStats stats = (DashboardStats) request.getAttribute("stats");

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    // Redirect if not a storekeeper
    if (!"STOREKEEPER".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect(request.getContextPath() + "/DashboardServlet");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Storekeeper Dashboard - Cleaning Inventory System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #eee; padding-bottom: 15px; margin-bottom: 20px; }
        .header h2 { color: #2c3e50; margin: 0; }
        .user-info { display: flex; align-items: center; gap: 15px; flex-wrap: wrap; }
        .user-info span { color: #555; }
        .role-badge { background: #27ae60; color: white; padding: 3px 12px; border-radius: 12px; font-size: 12px; font-weight: bold; }
        .user-info a { color: white; background: #e74c3c; padding: 5px 15px; text-decoration: none; border-radius: 4px; font-size: 14px; }
        .user-info a:hover { background: #c0392b; }
        .welcome-section { background: #ecf0f1; padding: 15px 20px; border-radius: 8px; margin-bottom: 25px; }
        .welcome-section h3 { margin: 0; color: #2c3e50; }
        .welcome-section p { margin: 5px 0 0 0; color: #7f8c8d; }
        .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 15px; margin-bottom: 30px; }
        .stat-card { background: #ecf0f1; padding: 15px 20px; border-radius: 8px; text-align: center; }
        .stat-card .number { font-size: 28px; font-weight: bold; color: #2c3e50; }
        .stat-card .label { color: #7f8c8d; font-size: 13px; margin-top: 5px; }
        .stat-card .number.green { color: #27ae60; }
        .stat-card .number.orange { color: #f39c12; }
        .stat-card .number.blue { color: #3498db; }
        .stat-card .number.red { color: #e74c3c; }
        .section-title { color: #2c3e50; font-size: 16px; border-bottom: 2px solid #ecf0f1; padding-bottom: 10px; margin-bottom: 15px; }
        .action-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px; margin-top: 15px; }
        .action-card { background: #ecf0f1; padding: 20px; border-radius: 8px; text-align: center; transition: transform 0.2s; border: 2px solid transparent; }
        .action-card:hover { transform: translateY(-3px); border-color: #27ae60; }
        .action-card .icon { font-size: 36px; margin-bottom: 10px; }
        .action-card h4 { margin: 0 0 10px 0; color: #2c3e50; }
        .action-card p { color: #7f8c8d; font-size: 13px; margin: 0 0 15px 0; }
        .action-card a { display: inline-block; padding: 8px 20px; background: #27ae60; color: white; text-decoration: none; border-radius: 4px; font-weight: bold; }
        .action-card a:hover { background: #219a52; }
        .action-card .view-btn { background: #3498db; }
        .action-card .view-btn:hover { background: #2980b9; }
        .action-card .warning-btn { background: #f1c40f; color: #2c3e50; }
        .action-card .warning-btn:hover { background: #d4ac0d; }
        .action-card .purple-btn { background: #8e44ad; }
        .action-card .purple-btn:hover { background: #7d3c98; }
        .info-box { background: #d1ecf1; color: #0c5460; padding: 12px 15px; border-radius: 4px; margin-bottom: 20px; border: 1px solid #bee5eb; }
        .footer { text-align: center; color: #7f8c8d; font-size: 12px; margin-top: 30px; padding-top: 15px; border-top: 1px solid #ecf0f1; }
    </style>
</head>
<body>

<div class="container">
    <!-- Header -->
    <div class="header">
        <h2>Storekeeper Dashboard</h2>
        <div class="user-info">
            <span><%= user.getFirstName() %> <%= user.getLastName() %></span>
            <span class="role-badge"><%= user.getRole() %></span>
            <a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
        </div>
    </div>

    <!-- Welcome Section -->
    <div class="welcome-section">
        <h3>Welcome back, <%= user.getFirstName() %>!</h3>
        <p>You are logged in as a <strong>Storekeeper</strong>. You can view inventory, cleaners, suppliers, and report issues.</p>
    </div>

    <!-- Statistics -->
    <h3 class="section-title">Inventory Overview</h3>
    <div class="stats-grid">
        <div class="stat-card">
            <div class="number blue"><%= stats != null ? stats.getTotalMaterials() : 0 %></div>
            <div class="label">Total Materials</div>
        </div>
        <div class="stat-card">
            <div class="number red"><%= stats != null ? stats.getLowStockItems() : 0 %></div>
            <div class="label">Low Stock Items</div>
        </div>
        <div class="stat-card">
            <div class="number green"><%= stats != null ? stats.getTotalCleaners() : 0 %></div>
            <div class="label">Active Cleaners</div>
        </div>
        <div class="stat-card">
            <div class="number blue"><%= stats != null ? stats.getTotalSuppliers() : 0 %></div>
            <div class="label">Active Suppliers</div>
        </div>
    </div>

    <!-- Modules -->
    <h3 class="section-title">Modules</h3>
    <div class="action-grid">
        <!-- View Materials -->
        <div class="action-card">
            <div class="icon"></div>
            <h4>Materials</h4>
            <p>View all materials in inventory</p>
            <a href="${pageContext.request.contextPath}/materials?action=list" class="view-btn">View Materials</a>
        </div>

        <!-- View Cleaners -->
        <div class="action-card">
            <div class="icon"></div>
            <h4>Cleaners</h4>
            <p>View all cleaners and their status</p>
            <a href="${pageContext.request.contextPath}/CleanerServlet?action=list" class="view-btn">View Cleaners</a>
        </div>

        <!-- View Suppliers -->
        <div class="action-card">
            <div class="icon"></div>
            <h4>Suppliers</h4>
            <p>View all suppliers and their details</p>
            <a href="${pageContext.request.contextPath}/SupplierServlet?action=list" class="view-btn">View Suppliers</a>
        </div>

        <!-- Report Issue -->
        <div class="action-card">
            <div class="icon"></div>
            <h4>Report Issue</h4>
            <p>Report defective products, delays, shortages, or other issues</p>
            <a href="${pageContext.request.contextPath}/issues?action=new" class="warning-btn">Report Issue</a>
        </div>

        <!-- View My Issues -->
        <div class="action-card">
            <div class="icon"></div>
            <h4>My Issues</h4>
            <p>View all issues you have reported</p>
            <a href="${pageContext.request.contextPath}/issues" class="purple-btn">View My Issues</a>
        </div>
    </div>

    <!-- Footer -->
    <div class="footer">
        <p>Cleaning Inventory System - University Management &copy; 2026</p>
    </div>
</div>

</body>
</html>
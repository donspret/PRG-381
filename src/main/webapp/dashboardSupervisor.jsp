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
    <title>Dashboard - Cleaning Inventory System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 2px solid #eee;
            padding-bottom: 15px;
            margin-bottom: 20px;
        }
        .header h2 {
            color: #2c3e50;
            margin: 0;
        }
        .user-info {
            display: flex;
            align-items: center;
            gap: 15px;
            flex-wrap: wrap;
        }
        .user-info span {
            color: #555;
        }
        .role-badge {
            background: #3498db;
            color: white;
            padding: 3px 12px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: bold;
        }
        .user-info a {
            color: white;
            background: #e74c3c;
            padding: 5px 15px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
        }
        .user-info a:hover {
            background: #c0392b;
        }
        .welcome-section {
            background: #ecf0f1;
            padding: 15px 20px;
            border-radius: 8px;
            margin-bottom: 25px;
        }
        .welcome-section h3 {
            margin: 0;
            color: #2c3e50;
        }
        .welcome-section p {
            margin: 5px 0 0 0;
            color: #7f8c8d;
        }
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 15px;
            margin-bottom: 30px;
        }
        .stat-card {
            background: #ecf0f1;
            padding: 15px 20px;
            border-radius: 8px;
            text-align: center;
        }
        .stat-card .number {
            font-size: 28px;
            font-weight: bold;
            color: #2c3e50;
        }
        .stat-card .label {
            color: #7f8c8d;
            font-size: 13px;
            margin-top: 5px;
        }
        .stat-card .number.low-stock {
            color: #e74c3c;
        }
        .stat-card .number.green {
            color: #27ae60;
        }
        .stat-card .number.blue {
            color: #3498db;
        }
        .stat-card .number.orange {
            color: #f39c12;
        }
        .section-title {
            color: #2c3e50;
            font-size: 16px;
            border-bottom: 2px solid #ecf0f1;
            padding-bottom: 10px;
            margin-bottom: 15px;
        }
        .quick-actions {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
            gap: 10px;
            margin-top: 15px;
            margin-bottom: 30px;
        }
        .quick-actions a {
            display: block;
            padding: 12px;
            background: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            text-align: center;
            font-weight: bold;
            transition: background 0.3s;
        }
        .quick-actions a:hover {
            background: #2980b9;
        }
        .quick-actions a.green { background: #27ae60; }
        .quick-actions a.green:hover { background: #219a52; }
        .quick-actions a.orange { background: #f39c12; }
        .quick-actions a.orange:hover { background: #d68910; }
        .quick-actions a.purple { background: #8e44ad; }
        .quick-actions a.purple:hover { background: #7d3c98; }
        .quick-actions a.red { background: #e74c3c; }
        .quick-actions a.red:hover { background: #c0392b; }
        .quick-actions a.teal { background: #1abc9c; }
        .quick-actions a.teal:hover { background: #16a085; }
        .quick-actions a.dark { background: #2c3e50; }
        .quick-actions a.dark:hover { background: #1a252f; }
        .management-modules {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 15px;
            margin-top: 15px;
            margin-bottom: 30px;
        }
        .module-card {
            background: #ecf0f1;
            padding: 20px;
            border-radius: 8px;
            text-align: center;
            border: 2px solid transparent;
            transition: border-color 0.3s, transform 0.2s;
        }
        .module-card:hover {
            border-color: #3498db;
            transform: translateY(-3px);
        }
        .module-card h4 {
            margin: 0 0 10px 0;
            color: #2c3e50;
        }
        .module-card .module-icon {
            font-size: 32px;
            margin-bottom: 10px;
        }
        .module-card .module-links {
            display: flex;
            gap: 8px;
            justify-content: center;
            flex-wrap: wrap;
        }
        .module-card .module-links a {
            padding: 5px 12px;
            background: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-size: 12px;
            font-weight: bold;
        }
        .module-card .module-links a:hover {
            background: #2980b9;
        }
        .module-card .module-links a.green { background: #27ae60; }
        .module-card .module-links a.green:hover { background: #219a52; }
        .module-card .module-links a.orange { background: #f39c12; }
        .module-card .module-links a.orange:hover { background: #d68910; }
        .module-card .module-links a.purple { background: #8e44ad; }
        .module-card .module-links a.purple:hover { background: #7d3c98; }
        .module-card .module-links a.red { background: #e74c3c; }
        .module-card .module-links a.red:hover { background: #c0392b; }
        .footer {
            text-align: center;
            color: #7f8c8d;
            font-size: 12px;
            margin-top: 30px;
            padding-top: 15px;
            border-top: 1px solid #ecf0f1;
        }
        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                text-align: center;
                gap: 10px;
            }
            .user-info {
                justify-content: center;
            }
            .stats-grid {
                grid-template-columns: repeat(2, 1fr);
            }
            .quick-actions {
                grid-template-columns: 1fr;
            }
            .management-modules {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>

<div class="container">
    <!-- Header -->
    <div class="header">
        <h2>🧹 Cleaning Inventory System</h2>
        <div class="user-info">
            <span>👤 <%= user.getFirstName() %> <%= user.getLastName() %></span>
            <span class="role-badge"><%= user.getRole() %></span>
            <a href="LogoutServlet">Logout</a>
        </div>
    </div>

    <!-- Welcome Section -->
    <div class="welcome-section">
        <h3>Welcome back, <%= user.getFirstName() %>! 👋</h3>
        <p>You are logged in as <strong><%= user.getRole() %></strong>. Manage suppliers, cleaners, materials, and issuances from here.</p>
    </div>

    <!-- Statistics -->
    <h3 class="section-title">📊 Dashboard Statistics</h3>
    <div class="stats-grid">
        <div class="stat-card">
            <div class="number blue"><%= stats != null ? stats.getTotalMaterials() : 0 %></div>
            <div class="label">Total Materials</div>
        </div>
        <div class="stat-card">
            <div class="number low-stock"><%= stats != null ? stats.getLowStockItems() : 0 %></div>
            <div class="label">⚠️ Low Stock Items</div>
        </div>
        <div class="stat-card">
            <div class="number green"><%= stats != null ? stats.getTotalCleaners() : 0 %></div>
            <div class="label">🧹 Total Cleaners</div>
        </div>
        <div class="stat-card">
            <div class="number orange"><%= stats != null ? stats.getTotalSuppliers() : 0 %></div>
            <div class="label">📦 Total Suppliers</div>
        </div>
        <div class="stat-card">
            <div class="number blue"><%= stats != null ? stats.getRecentIssuances() : 0 %></div>
            <div class="label">📋 Recent Issuances</div>
        </div>
        <div class="stat-card">
            <div class="number"><%= stats != null ? stats.getTotalUsers() : 0 %></div>
            <div class="label">👤 Total Users</div>
        </div>
    </div>

    <!-- Quick Actions -->
    <h3 class="section-title">⚡ Quick Actions</h3>
    <div class="quick-actions">
        <a href="${pageContext.request.contextPath}/SupplierServlet?action=add" class="orange">➕ Add Supplier</a>
        <a href="${pageContext.request.contextPath}/SupplierServlet?action=list" class="orange">📋 View Suppliers</a>
        <a href="${pageContext.request.contextPath}/CleanerServlet?action=add" class="green">➕ Add Cleaner</a>
        <a href="${pageContext.request.contextPath}/CleanerServlet?action=list" class="green">📋 View Cleaners</a>
        <a href="${pageContext.request.contextPath}/materials?action=new" class="blue">➕ Add Material</a>
        <a href="${pageContext.request.contextPath}/materials?action=lowstock" class="red">⚠️ View Low Stock</a>
        <a href="${pageContext.request.contextPath}/issuance?action=new" class="purple">📤 New Issuance</a>
        <a href="${pageContext.request.contextPath}/reports" class="teal">📊 Generate Reports</a>
    </div>

    <!-- Management Modules -->
    <h3 class="section-title">📁 Management Modules</h3>
    <div class="management-modules">
        <!-- Suppliers Module -->
        <div class="module-card">
            <div class="module-icon">🏢</div>
            <h4>Suppliers</h4>
            <div class="module-links">
                <a href="${pageContext.request.contextPath}/SupplierServlet?action=list" class="orange">View All</a>
                <a href="${pageContext.request.contextPath}/SupplierServlet?action=add" class="orange">Add New</a>
            </div>
        </div>

        <!-- Cleaners Module -->
        <div class="module-card">
            <div class="module-icon">🧹</div>
            <h4>Cleaners</h4>
            <div class="module-links">
                <a href="${pageContext.request.contextPath}/CleanerServlet?action=list" class="green">View All</a>
                <a href="${pageContext.request.contextPath}/CleanerServlet?action=add" class="green">Add New</a>
            </div>
        </div>

        <!-- Materials Module -->
        <div class="module-card">
            <div class="module-icon">📦</div>
            <h4>Materials</h4>
            <div class="module-links">
                <a href="${pageContext.request.contextPath}/materials?action=list" class="blue">View All</a>
                <a href="${pageContext.request.contextPath}/materials?action=new" class="blue">Add New</a>
                <a href="${pageContext.request.contextPath}/materials?action=lowstock" class="red">Low Stock</a>
            </div>
        </div>

        <!-- Issuances Module -->
        <div class="module-card">
            <div class="module-icon">📋</div>
            <h4>Issuances</h4>
            <div class="module-links">
                <a href="${pageContext.request.contextPath}/issuance?action=list" class="purple">View All</a>
                <a href="${pageContext.request.contextPath}/issuance?action=new" class="purple">New Issuance</a>
            </div>
        </div>

        <!-- Reports Module -->
        <div class="module-card">
            <div class="module-icon">📊</div>
            <h4>Reports</h4>
            <div class="module-links">
                <a href="${pageContext.request.contextPath}/reports" class="teal">Generate Reports</a>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <div class="footer">
        <p>Cleaning Inventory System - University Management &copy; 2026</p>
        <p>Developed for Programming 37(8)1</p>
    </div>
</div>

</body>
</html>
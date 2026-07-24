<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleaninginventory.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Reports - Cleaning Inventory System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #eee; padding-bottom: 15px; margin-bottom: 20px; }
        .header h2 { color: #2c3e50; margin: 0; }
        .header a { color: white; background: #e74c3c; padding: 5px 15px; text-decoration: none; border-radius: 4px; font-size: 14px; }
        .header a:hover { background: #c0392b; }
        .header .dashboard-btn { background: #3498db; }
        .header .dashboard-btn:hover { background: #2980b9; }
        .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 20px; margin-bottom: 30px; }
        .stat-card { background: #ecf0f1; padding: 20px; border-radius: 8px; text-align: center; }
        .stat-card .number { font-size: 32px; font-weight: bold; color: #2c3e50; }
        .stat-card .label { color: #7f8c8d; font-size: 14px; margin-top: 5px; }
        .stat-card .number.green { color: #27ae60; }
        .stat-card .number.orange { color: #f39c12; }
        .stat-card .number.red { color: #e74c3c; }
        .stat-card .number.blue { color: #3498db; }
        .report-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px; margin-top: 15px; }
        .report-card { background: #f8f9fa; padding: 25px; border-radius: 8px; text-align: center; border: 2px solid #e9ecef; transition: transform 0.2s, border-color 0.2s; }
        .report-card:hover { transform: translateY(-5px); border-color: #3498db; }
        .report-card .icon { font-size: 48px; margin-bottom: 15px; }
        .report-card h4 { margin: 0 0 10px 0; color: #2c3e50; }
        .report-card p { color: #7f8c8d; font-size: 14px; margin: 0 0 15px 0; }
        .report-card a { display: inline-block; padding: 8px 25px; background: #3498db; color: white; text-decoration: none; border-radius: 4px; font-weight: bold; }
        .report-card a:hover { background: #2980b9; }
        .report-card a.green { background: #27ae60; }
        .report-card a.green:hover { background: #219a52; }
        .report-card a.orange { background: #f39c12; }
        .report-card a.orange:hover { background: #d68910; }
        .report-card a.red { background: #e74c3c; }
        .report-card a.red:hover { background: #c0392b; }
        .section-title { color: #2c3e50; font-size: 18px; border-bottom: 2px solid #ecf0f1; padding-bottom: 10px; margin-bottom: 20px; }
        .footer { text-align: center; color: #7f8c8d; font-size: 12px; margin-top: 30px; padding-top: 15px; border-top: 1px solid #ecf0f1; }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h2>Reports Dashboard</h2>
        <div>
            <a href="${pageContext.request.contextPath}/DashboardServlet" class="dashboard-btn">Dashboard</a>
            <a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
        </div>
    </div>

    <div class="welcome-section" style="background: #ecf0f1; padding: 15px 20px; border-radius: 8px; margin-bottom: 25px;">
        <h3 style="margin: 0; color: #2c3e50;">Generate Reports</h3>
        <p style="margin: 5px 0 0 0; color: #7f8c8d;">View inventory, low stock, issuance history, and material usage reports.</p>
    </div>

    <!-- Summary Stats -->
    <h3 class="section-title">Summary</h3>
    <div class="stats-grid">
        <div class="stat-card">
            <div class="number blue"><%= request.getAttribute("totalMaterials") != null ? request.getAttribute("totalMaterials") : 0 %></div>
            <div class="label">Total Materials</div>
        </div>
        <div class="stat-card">
            <div class="number red"><%= request.getAttribute("lowStockItems") != null ? request.getAttribute("lowStockItems") : 0 %></div>
            <div class="label">Low Stock Items</div>
        </div>
        <div class="stat-card">
            <div class="number green"><%= request.getAttribute("totalIssuances") != null ? request.getAttribute("totalIssuances") : 0 %></div>
            <div class="label">Total Issuances</div>
        </div>
        <div class="stat-card">
            <div class="number orange"><%= request.getAttribute("openIssues") != null ? request.getAttribute("openIssues") : 0 %></div>
            <div class="label">Open Issues</div>
        </div>
    </div>

    <!-- Report Options -->
    <h3 class="section-title">📄 Available Reports</h3>
    <div class="report-grid">
        <!-- Inventory Report -->
        <div class="report-card">
            <div class="icon"></div>
            <h4>Inventory Report</h4>
            <p>Complete inventory of all materials with current stock levels</p>
            <a href="${pageContext.request.contextPath}/reports?type=inventory">Generate</a>
        </div>

        <!-- Low Stock Report -->
        <div class="report-card">
            <div class="icon"></div>
            <h4>Low Stock Report</h4>
            <p>Materials that are below or at reorder level</p>
            <a href="${pageContext.request.contextPath}/reports?type=lowstock" class="red">Generate</a>
        </div>

        <!-- Issuance History -->
        <div class="report-card">
            <div class="icon"></div>
            <h4>Issuance History</h4>
            <p>Complete history of all stock issuances</p>
            <a href="${pageContext.request.contextPath}/reports?type=issuance" class="orange">Generate</a>
        </div>

        <!-- Material Usage Report -->
        <div class="report-card">
            <div class="icon"></div>
            <h4>Material Usage Report</h4>
            <p>Material consumption and remaining stock analysis</p>
            <a href="${pageContext.request.contextPath}/reports?type=usage" class="green">Generate</a>
        </div>
    </div>

    <div class="footer">
        <p>Cleaning Inventory System - University Management &copy; 2026</p>
    </div>
</div>

</body>
</html>
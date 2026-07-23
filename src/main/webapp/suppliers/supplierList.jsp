<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleaninginventory.model.User" %>
<%@ page import="com.cleaninginventory.model.Supplier" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    List<Supplier> suppliers = (List<Supplier>) request.getAttribute("suppliers");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Suppliers Management - Cleaning Inventory System</title>
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
        .header a {
            color: white;
            background: #e74c3c;
            padding: 5px 15px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
        }
        .header a:hover {
            background: #c0392b;
        }
        .header .add-btn {
            background: #27ae60;
        }
        .header .add-btn:hover {
            background: #219a52;
        }
        .search-box {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
        }
        .search-box input {
            padding: 8px;
            width: 300px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .search-box .btn {
            padding: 8px 16px;
            background: #3498db;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .search-box .btn:hover {
            background: #2980b9;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }
        th {
            background: #2c3e50;
            color: white;
        }
        tr:hover {
            background: #f5f5f5;
        }
        .status-active {
            color: green;
            font-weight: bold;
        }
        .status-inactive {
            color: red;
            font-weight: bold;
        }
        .btn-edit {
            background: #f39c12;
            color: white;
            padding: 4px 10px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 12px;
        }
        .btn-edit:hover {
            background: #d68910;
        }
        .btn-delete {
            background: #e74c3c;
            color: white;
            padding: 4px 10px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 12px;
        }
        .btn-delete:hover {
            background: #c0392b;
        }
        .alert {
            padding: 12px 15px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .no-data {
            text-align: center;
            color: #7f8c8d;
            padding: 20px;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h2>🏢 Suppliers Management</h2>
        <div>
            <a href="${pageContext.request.contextPath}/SupplierServlet?action=add" class="add-btn">➕ Add Supplier</a>
            <a href="${pageContext.request.contextPath}/DashboardServlet">📊 Dashboard</a>
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

    <div class="search-box">
        <form action="${pageContext.request.contextPath}/SupplierServlet" method="get">
            <input type="hidden" name="action" value="search">
            <input type="text" name="keyword" placeholder="Search by company, contact person or email..." value="<%= request.getAttribute("keyword") != null ? request.getAttribute("keyword") : "" %>">
            <button type="submit" class="btn">Search</button>
        </form>
        <a href="${pageContext.request.contextPath}/SupplierServlet?action=list" class="btn" style="background:#95a5a6;">Clear</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Company Name</th>
                <th>Contact Person</th>
                <th>Email</th>
                <th>Phone</th>
                <th>City</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (suppliers != null && !suppliers.isEmpty()) {
                    for (Supplier supplier : suppliers) {
            %>
            <tr>
                <td><%= supplier.getSupplierId() %></td>
                <td><%= supplier.getCompanyName() %></td>
                <td><%= supplier.getContactPerson() %></td>
                <td><%= supplier.getEmail() %></td>
                <td><%= supplier.getPhone() %></td>
                <td><%= supplier.getCity() != null ? supplier.getCity() : "" %></td>
                <td>
                    <span class="<%= supplier.getStatus().equals("Active") ? "status-active" : "status-inactive" %>">
                        <%= supplier.getStatus() %>
                    </span>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/SupplierServlet?action=edit&id=<%= supplier.getSupplierId() %>" class="btn-edit">Edit</a>
                    <a href="${pageContext.request.contextPath}/SupplierServlet?action=delete&id=<%= supplier.getSupplierId() %>"
                       class="btn-delete"
                       onclick="return confirm('Are you sure you want to delete this supplier?')">Delete</a>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="8" class="no-data">No suppliers found.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</div>

</body>
</html>
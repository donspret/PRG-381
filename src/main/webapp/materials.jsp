<%--
  Created by IntelliJ IDEA.
  User: donya
  Date: 2026/07/12
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.final_prg381.model.Material" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Materials Management</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        table { border-collapse: collapse; width: 100%; margin-top: 15px; }
        th, td { border: 1px solid #ccc; padding: 8px 12px; text-align: left; }
        th { background-color: #2c3e50; color: white; }
        .low-stock { background-color: #ffcccc; }
        .btn { padding: 5px 10px; text-decoration: none; color: white; border-radius: 4px; font-size: 13px; }
        .btn-edit { background-color: #2980b9; }
        .btn-delete { background-color: #c0392b; }
        .btn-add { background-color: #27ae60; padding: 8px 16px; }
        .search-box { margin-top: 15px; }
        .error { color: red; }
    </style>
</head>
<body>

<h1>Materials Management</h1>

<%
    Boolean lowStockView = (Boolean) request.getAttribute("lowStockView");
    if (lowStockView != null && lowStockView) {
%>
<p><strong>Showing low-stock items only.</strong> <a href="materials">View all materials</a></p>
<%
} else {
%>
<p><a href="materials?action=lowstock" style="color:#c0392b;"><strong>View Low-Stock Alerts</strong></a></p>
<%
    }
%>

<a class="btn btn-add" href="materials?action=new">+ Add New Material</a>

<div class="search-box">
    <form action="materials" method="get">
        <input type="hidden" name="action" value="search">
        <input type="text" name="keyword" placeholder="Search by name"
               value="${keyword != null ? keyword : ''}">
        <input type="text" name="category" placeholder="Filter by category"
               value="${category != null ? category : ''}">
        <button type="submit">Search</button>
        <a href="materials">Clear</a>
    </form>
</div>

<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Category</th>
        <th>Unit</th>
        <th>Qty in Stock</th>
        <th>Low Stock Threshold</th>
        <th>Unit Price</th>
        <th>Supplier ID</th>
        <th>Actions</th>
    </tr>

    <%
        List<Material> materials = (List<Material>) request.getAttribute("materials");
        if (materials != null) {
            for (Material m : materials) {
                String rowClass = m.isLowStock() ? "low-stock" : "";
    %>
    <tr class="<%= rowClass %>">
        <td><%= m.getMaterialId() %></td>
        <td><%= m.getMaterialName() %></td>
        <td><%= m.getCategory() %></td>
        <td><%= m.getUnit() %></td>
        <td><%= m.getQuantityInStock() %></td>
        <td><%= m.getLowStockThreshold() %></td>
        <td><%= m.getUnitPrice() %></td>
        <td><%= m.getSupplierId() %></td>
        <td>
            <a class="btn btn-edit" href="materials?action=edit&id=<%= m.getMaterialId() %>">Edit</a>
            <a class="btn btn-delete" href="materials?action=delete&id=<%= m.getMaterialId() %>"
               onclick="return confirm('Are you sure you want to delete this material?');">Delete</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>

</body>
</html>

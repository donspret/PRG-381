<%--
  Created by IntelliJ IDEA.
  User: donya
  Date: 2026/07/12
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.final_prg381.model.Material" %>
<!DOCTYPE html>
<html>
<head>
    <title>Material Form</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input { padding: 6px; width: 250px; margin-top: 3px; }
        .btn-save { margin-top: 15px; padding: 8px 16px; background-color: #27ae60; color: white; border: none; border-radius: 4px; cursor: pointer; }
        .error { color: red; font-weight: bold; }
    </style>
</head>
<body>

<%
    Material material = (Material) request.getAttribute("material");
    boolean isEdit = (material != null && material.getMaterialId() > 0);
    String errorMessage = (String) request.getAttribute("errorMessage");
%>

<h1><%= isEdit ? "Edit Material" : "Add New Material" %></h1>

<% if (errorMessage != null) { %>
<p class="error"><%= errorMessage %></p>
<% } %>

<form action="materials" method="post">
    <input type="hidden" name="action" value="<%= isEdit ? "update" : "insert" %>">
    <% if (isEdit) { %>
    <input type="hidden" name="materialId" value="<%= material.getMaterialId() %>">
    <% } %>

    <label>Material Name</label>
    <input type="text" name="materialName" required
           value="<%= material != null && material.getMaterialName() != null ? material.getMaterialName() : "" %>">

    <label>Category</label>
    <input type="text" name="category" required
           value="<%= material != null && material.getCategory() != null ? material.getCategory() : "" %>">

    <label>Unit (e.g. bottle, box, litre)</label>
    <input type="text" name="unit" required
           value="<%= material != null && material.getUnit() != null ? material.getUnit() : "" %>">

    <label>Quantity in Stock</label>
    <input type="number" name="quantityInStock" required min="0"
           value="<%= material != null ? material.getQuantityInStock() : 0 %>">

    <label>Low Stock Threshold</label>
    <input type="number" name="lowStockThreshold" required min="0"
           value="<%= material != null ? material.getLowStockThreshold() : 10 %>">

    <label>Unit Price</label>
    <input type="number" step="0.01" name="unitPrice" required min="0.01"
           value="<%= material != null && material.getUnitPrice() != null ? material.getUnitPrice() : "" %>">

    <label>Supplier ID</label>
    <input type="number" name="supplierId" required min="1"
           value="<%= material != null ? material.getSupplierId() : "" %>">

    <br>
    <button class="btn-save" type="submit"><%= isEdit ? "Update Material" : "Add Material" %></button>
    <a href="materials" style="margin-left:10px;">Cancel</a>
</form>

</body>
</html>
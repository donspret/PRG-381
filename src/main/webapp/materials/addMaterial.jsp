<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleaninginventory.model.User" %>
<%@ page import="com.cleaninginventory.model.Material" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    Material material = (Material) request.getAttribute("material");
    boolean isEdit = (material != null && material.getMaterialId() > 0);
    String errorMessage = (String) request.getAttribute("errorMessage");
    String successMessage = (String) request.getAttribute("successMessage");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= isEdit ? "Edit" : "Add" %> Material - Cleaning Inventory System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { max-width: 600px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #eee; padding-bottom: 15px; margin-bottom: 20px; }
        .header h2 { color: #2c3e50; margin: 0; }
        .header a { color: white; background: #e74c3c; padding: 5px 15px; text-decoration: none; border-radius: 4px; font-size: 14px; }
        .header a:hover { background: #c0392b; }
        .header .back-btn { background: #3498db; }
        .header .back-btn:hover { background: #2980b9; }
        .form-group { margin-bottom: 15px; }
        label { display: block; font-weight: bold; margin-bottom: 5px; color: #333; }
        input, select, textarea { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; font-size: 14px; }
        input:focus, select:focus, textarea:focus { border-color: #3498db; outline: none; }
        .required { color: red; }
        .btn { padding: 10px 20px; background-color: #27ae60; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 14px; font-weight: bold; }
        .btn:hover { background-color: #219a52; }
        .btn-cancel { background-color: #95a5a6; margin-left: 10px; }
        .btn-cancel:hover { background-color: #7f8c8d; }
        .alert { padding: 12px 15px; margin-bottom: 20px; border-radius: 4px; }
        .alert-error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .help-text { font-size: 12px; color: #7f8c8d; margin-top: 5px; }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h2><%= isEdit ? "✏️ Edit" : "➕ Add" %> Material</h2>
        <div>
            <a href="${pageContext.request.contextPath}/materials?action=list" class="back-btn">← Back to Materials</a>
            <a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
        </div>
    </div>

    <%
        if (errorMessage != null) {
    %>
        <div class="alert alert-error"><%= errorMessage %></div>
    <%
        }
        if (successMessage != null) {
    %>
        <div class="alert alert-success"><%= successMessage %></div>
    <%
        }
    %>

    <form action="${pageContext.request.contextPath}/materials" method="post">
        <input type="hidden" name="action" value="<%= isEdit ? "update" : "add" %>">
        <% if (isEdit) { %>
            <input type="hidden" name="materialId" value="<%= material.getMaterialId() %>">
        <% } %>

        <div class="form-group">
            <label for="materialName">Material Name <span class="required">*</span></label>
            <input type="text" id="materialName" name="materialName" value="<%= isEdit ? material.getMaterialName() : "" %>" required placeholder="e.g., Cleaning Solution">
        </div>

        <div class="form-group">
            <label for="category">Category</label>
            <input type="text" id="category" name="category" value="<%= isEdit && material.getCategory() != null ? material.getCategory() : "" %>" placeholder="e.g., Cleaning Chemicals">
        </div>

        <div class="form-group">
            <label for="unit">Unit of Measurement</label>
            <input type="text" id="unit" name="unit" value="<%= isEdit && material.getUnit() != null ? material.getUnit() : "" %>" placeholder="e.g., bottles, pieces, cans">
            <div class="help-text">e.g., bottles, pieces, cans, liters</div>
        </div>

        <div class="form-group">
            <label for="quantityInStock">Quantity <span class="required">*</span></label>
            <input type="number" id="quantityInStock" name="quantityInStock" min="0" value="<%= isEdit ? material.getQuantityInStock() : 0 %>" required>
            <div class="help-text">Current stock quantity</div>
        </div>

        <div class="form-group">
            <label for="lowStockThreshold">Low Stock Threshold <span class="required">*</span></label>
            <input type="number" id="lowStockThreshold" name="lowStockThreshold" min="0" value="<%= isEdit ? material.getLowStockThreshold() : 5 %>" required>
            <div class="help-text">When quantity falls below this level, it will show as <strong>Low Stock</strong></div>
        </div>

        <div class="form-group">
            <label for="unitPrice">Unit Price</label>
            <input type="number" id="unitPrice" name="unitPrice" step="0.01" min="0" value="<%= isEdit && material.getUnitPrice() != null ? material.getUnitPrice() : "0.00" %>" placeholder="0.00">
        </div>

        <div class="form-group">
            <label for="supplierId">Supplier</label>
            <input type="number" id="supplierId" name="supplierId" min="0" value="<%= isEdit && material.getSupplierId() > 0 ? material.getSupplierId() : "" %>" placeholder="Supplier ID">
            <div class="help-text">Enter the Supplier ID</div>
        </div>

        <button type="submit" class="btn"><%= isEdit ? "Update" : "Add" %> Material</button>
        <a href="${pageContext.request.contextPath}/materials?action=list" class="btn btn-cancel">Cancel</a>
    </form>
</div>

</body>
</html>
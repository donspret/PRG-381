<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleaninginventory.model.User" %>
<%@ page import="com.cleaninginventory.model.Supplier" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    Supplier supplier = (Supplier) request.getAttribute("supplier");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Supplier - Cleaning Inventory System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 600px;
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
        .header .back-btn {
            background: #3498db;
        }
        .header .back-btn:hover {
            background: #2980b9;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
            color: #333;
        }
        input, select, textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }
        input:focus, select:focus, textarea:focus {
            border-color: #3498db;
            outline: none;
        }
        .required {
            color: red;
        }
        .btn {
            padding: 10px 20px;
            background-color: #f39c12;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: bold;
        }
        .btn:hover {
            background-color: #d68910;
        }
        .btn-cancel {
            background-color: #95a5a6;
            margin-left: 10px;
        }
        .btn-cancel:hover {
            background-color: #7f8c8d;
        }
        .alert {
            padding: 12px 15px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h2>✏Edit Supplier</h2>
        <div>
            <a href="${pageContext.request.contextPath}/SupplierServlet?action=list" class="back-btn">← Back to Suppliers</a>
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
        if (supplier == null) {
    %>
        <div class="alert alert-error">Supplier not found.</div>
    <%
        } else {
    %>

    <form action="${pageContext.request.contextPath}/SupplierServlet" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="supplierId" value="<%= supplier.getSupplierId() %>">

        <div class="form-group">
            <label for="companyName">Company Name <span class="required">*</span></label>
            <input type="text" id="companyName" name="companyName" value="<%= supplier.getCompanyName() %>" required>
        </div>

        <div class="form-group">
            <label for="contactPerson">Contact Person <span class="required">*</span></label>
            <input type="text" id="contactPerson" name="contactPerson" value="<%= supplier.getContactPerson() %>" required>
        </div>

        <div class="form-group">
            <label for="email">Email <span class="required">*</span></label>
            <input type="email" id="email" name="email" value="<%= supplier.getEmail() %>" required>
        </div>

        <div class="form-group">
            <label for="phone">Phone <span class="required">*</span></label>
            <input type="tel" id="phone" name="phone" value="<%= supplier.getPhone() %>" required>
        </div>

        <div class="form-group">
            <label for="address">Address</label>
            <input type="text" id="address" name="address" value="<%= supplier.getAddress() != null ? supplier.getAddress() : "" %>">
        </div>

        <div class="form-group">
            <label for="city">City</label>
            <input type="text" id="city" name="city" value="<%= supplier.getCity() != null ? supplier.getCity() : "" %>">
        </div>

        <div class="form-group">
            <label for="postalCode">Postal Code</label>
            <input type="text" id="postalCode" name="postalCode" value="<%= supplier.getPostalCode() != null ? supplier.getPostalCode() : "" %>">
        </div>

        <div class="form-group">
            <label for="country">Country</label>
            <input type="text" id="country" name="country" value="<%= supplier.getCountry() != null ? supplier.getCountry() : "South Africa" %>">
        </div>

        <div class="form-group">
            <label for="status">Status</label>
            <select id="status" name="status">
                <option value="Active" <%= supplier.getStatus().equals("Active") ? "selected" : "" %>>Active</option>
                <option value="Inactive" <%= supplier.getStatus().equals("Inactive") ? "selected" : "" %>>Inactive</option>
            </select>
        </div>

        <div class="form-group">
            <label for="notes">Notes</label>
            <textarea id="notes" name="notes" rows="3"><%= supplier.getNotes() != null ? supplier.getNotes() : "" %></textarea>
        </div>

        <button type="submit" class="btn">Update Supplier</button>
        <a href="${pageContext.request.contextPath}/SupplierServlet?action=list" class="btn btn-cancel">Cancel</a>
    </form>

    <%
        }
    %>
</div>

</body>
</html>
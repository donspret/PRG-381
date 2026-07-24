<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleaninginventory.model.User" %>
<%@ page import="com.cleaninginventory.model.Material" %>
<%@ page import="com.cleaninginventory.model.Supplier" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    List<Material> materials = (List<Material>) request.getAttribute("materials");
    List<Supplier> suppliers = (List<Supplier>) request.getAttribute("suppliers");
    String[] issueTypes = (String[]) request.getAttribute("issueTypes");
    String[] severities = (String[]) request.getAttribute("severities");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Report Issue - Cleaning Inventory System</title>
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
        <h2>Report Issue</h2>
        <div>
            <a href="${pageContext.request.contextPath}/issues" class="back-btn">← My Issues</a>
            <%
                if ("STOREKEEPER".equalsIgnoreCase(user.getRole())) {
            %>
                <a href="${pageContext.request.contextPath}/storekeeper/dashboard" class="back-btn">Dashboard</a>
            <%
                } else {
            %>
                <a href="${pageContext.request.contextPath}/DashboardServlet" class="back-btn">Dashboard</a>
            <%
                }
            %>
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

    <form action="${pageContext.request.contextPath}/issues" method="post">
        <input type="hidden" name="action" value="create">

        <div class="form-group">
            <label for="issueType">Issue Type <span class="required">*</span></label>
            <select id="issueType" name="issueType" required>
                <option value="">Select Issue Type</option>
                <%
                    for (String type : issueTypes) {
                %>
                    <option value="<%= type %>"><%= type %></option>
                <%
                    }
                %>
            </select>
        </div>

        <div class="form-group">
            <label for="description">Description <span class="required">*</span></label>
            <textarea id="description" name="description" rows="4" placeholder="Describe the issue in detail..." required></textarea>
        </div>

        <div class="form-group">
            <label for="severity">Severity</label>
            <select id="severity" name="severity">
                <%
                    for (String severity : severities) {
                %>
                    <option value="<%= severity %>"><%= severity %></option>
                <%
                    }
                %>
            </select>
            <div class="help-text">How critical is this issue?</div>
        </div>

        <div class="form-group">
            <label for="materialId">Related Material</label>
            <select id="materialId" name="materialId">
                <option value="0">None</option>
                <%
                    if (materials != null) {
                        for (Material material : materials) {
                %>
                    <option value="<%= material.getMaterialId() %>"><%= material.getMaterialName() %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <div class="form-group">
            <label for="supplierId">Related Supplier</label>
            <select id="supplierId" name="supplierId">
                <option value="0">None</option>
                <%
                    if (suppliers != null) {
                        for (Supplier supplier : suppliers) {
                %>
                    <option value="<%= supplier.getSupplierId() %>"><%= supplier.getCompanyName() %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <button type="submit" class="btn">Submit Issue</button>
        <a href="${pageContext.request.contextPath}/issues" class="btn btn-cancel">Cancel</a>
    </form>
</div>

</body>
</html>
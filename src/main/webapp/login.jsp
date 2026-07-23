<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Login - Cleaning Inventory System</title>
</head>

<body>

<h2>Cleaning Inventory System</h2>
<p>University Cleaning Management System</p>

<form action="LoginServlet" method="post">

    <label for="username">Username:</label>
    <input type="text" id="username" name="username" placeholder="Enter your username" required>

    <br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" placeholder="Enter your password" required>

    <br><br>

    <button type="submit"> Login </button>

</form>

<p>
    Don't have an account? <a href="register.jsp">Register here</a>
</p>

</body>
</html>
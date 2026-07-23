<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Register - Cleaning Inventory System</title>
</head>

<body>

<h2>Create Account</h2>
<p>Register for Cleaning Management System</p>

<form action="${pageContext.request.contextPath}/RegisterServlet" method="post">

    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" required>
    <br><br>

    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" required>
    <br><br>


    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>
    <br><br>

    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required>
    <br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>
    <br>
    <small>8+ chars, uppercase, lowercase, digit, special char (@#$%^&+=!)</small>
    <br><br>

    <label for="confirmPassword">Confirm Password:</label>
    <input type="password" id="confirmPassword" name="confirmPassword" required>

    <label for="role">Role:</label>
    <select id="role" name="role" required>
        <option value="">Select Role</option>
        <option value="Storekeeper">Storekeeper</option>
        <option value="Supervisor">Supervisor</option>
    </select>

    <br><br>

    <button type="submit">
        Register
    </button>

</form>

<p>
    Already have an account? <a href="login.jsp">Sign in here</a>
</p>
</body>
</html>
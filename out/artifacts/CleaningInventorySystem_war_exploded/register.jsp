<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Register</title>
</head>

<body>

<h2>Create Account</h2>

<form action="${pageContext.request.contextPath}/RegisterServlet" method="post">

    <label>First Name:</label>
    <input type="text" name="firstName">
    <br><br>

    <label>Last Name:</label>
    <input type="text" name="lastName">
    <br><br>

    <label>Email:</label>
    <input type="email" name="email">
    <br><br>

    <label>Username:</label>
    <input type="text" name="username">
    <br><br>

    <label>Password:</label>
    <input type="password" name="password">
    <br><br>

    <label>Role:</label>

    <select name="role">

        <option value="Storekeeper">
            Storekeeper
        </option>

        <option value="Supervisor">
            Supervisor
        </option>

    </select>

    <br><br>

    <button type="submit">
        Register
    </button>

</form>

</body>
</html>
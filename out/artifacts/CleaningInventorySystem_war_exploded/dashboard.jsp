<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="com.cleaninginventory.model.User" %>


<html>

<head>
    <title>Dashboard</title>
</head>


<body>


<%

    User user = (User) session.getAttribute("user");


    if(user == null){

        response.sendRedirect("login.jsp");

    }

%>


<h2>Welcome to Cleaning Inventory System</h2>


<p>
    Username:
    <%= user.getUsername() %>
</p>


<p>
    Role:
    <%= user.getRole() %>
</p>



<h3>Dashboard</h3>


<ul>

    <li>
        Materials Management
    </li>

    <li>
        Suppliers Management
    </li>

    <li>
        Cleaners Management
    </li>

    <li>
        Stock Issuance
    </li>

    <li>
        Reports
    </li>

</ul>


<a href="LogoutServlet">
    Logout
</a>


</body>

</html>
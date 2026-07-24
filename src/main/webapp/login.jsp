<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    String successMessage = (String) request.getAttribute("successMessage");
    String username = (String) request.getAttribute("username");
    if (username == null) username = "";
%>

<!DOCTYPE html>
<html>
<head>
    <title>Login - Cleaning Inventory System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background: #f4f4f4;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 20px;
        }

        .container {
            max-width: 450px;
            width: 100%;
            background: white;
            padding: 40px 35px;
            border-radius: 12px;
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
            animation: fadeIn 0.5s ease-in;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .header {
            text-align: center;
            margin-bottom: 30px;
            border-bottom: 2px solid #eee;
            padding-bottom: 20px;
        }

        .header h1 {
            color: #2c3e50;
            font-size: 26px;
            margin-bottom: 5px;
        }

        .header .subtitle {
            color: #7f8c8d;
            font-size: 14px;
        }

        .header .icon {
            font-size: 48px;
            display: block;
            margin-bottom: 10px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            color: #2c3e50;
            font-weight: 600;
            font-size: 14px;
            margin-bottom: 6px;
        }

        .form-group label .required {
            color: #e74c3c;
        }

        .form-group input {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #ddd;
            border-radius: 8px;
            font-size: 14px;
            transition: border-color 0.3s, box-shadow 0.3s;
            background: #fafafa;
        }

        .form-group input:focus {
            outline: none;
            border-color: #3498db;
            box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.15);
            background: white;
        }

        .form-group input.error {
            border-color: #e74c3c;
        }

        .form-group input.error:focus {
            box-shadow: 0 0 0 3px rgba(231, 76, 60, 0.15);
        }

        .form-group .field-error {
            color: #e74c3c;
            font-size: 12px;
            margin-top: 4px;
            display: none;
        }

        .form-group .field-error.show {
            display: block;
        }

        .btn-login {
            width: 100%;
            padding: 14px;
            background: #3498db;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s, transform 0.1s;
        }

        .btn-login:hover {
            background: #2980b9;
        }

        .btn-login:active {
            transform: scale(0.98);
        }

        .btn-login:disabled {
            opacity: 0.6;
            cursor: not-allowed;
        }

        .alert {
            padding: 12px 16px;
            border-radius: 8px;
            margin-bottom: 20px;
            display: none;
            animation: slideDown 0.3s ease-out;
        }

        @keyframes slideDown {
            from { opacity: 0; max-height: 0; }
            to { opacity: 1; max-height: 100px; }
        }

        .alert.show {
            display: block;
        }

        .alert-error {
            background: #fde8e8;
            border-left: 4px solid #e74c3c;
            color: #c0392b;
        }

        .alert-success {
            background: #e8f8f0;
            border-left: 4px solid #27ae60;
            color: #1a7a4a;
        }

        .alert .alert-icon {
            margin-right: 8px;
        }

        .footer-links {
            text-align: center;
            margin-top: 25px;
            padding-top: 20px;
            border-top: 1px solid #ecf0f1;
            font-size: 14px;
            color: #7f8c8d;
        }

        .footer-links a {
            color: #3498db;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.3s;
        }

        .footer-links a:hover {
            color: #2980b9;
            text-decoration: underline;
        }

        .footer-links .separator {
            margin: 0 10px;
            color: #ddd;
        }

        .spinner {
            display: inline-block;
            width: 18px;
            height: 18px;
            border: 3px solid rgba(255, 255, 255, 0.3);
            border-radius: 50%;
            border-top-color: #fff;
            animation: spin 0.8s linear infinite;
            vertical-align: middle;
            margin-right: 8px;
        }

        @keyframes spin {
            to { transform: rotate(360deg); }
        }

        @media (max-width: 480px) {
            .container {
                padding: 25px 20px;
            }

            .header h1 {
                font-size: 22px;
            }
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <span class="icon"></span>
        <h1>Cleaning Inventory System</h1>
        <p class="subtitle">University Cleaning Management System</p>
    </div>

    <!-- Error Alert -->
    <div id="errorAlert" class="alert alert-error <%= errorMessage != null && !errorMessage.isEmpty() ? "show" : "" %>">
        <span class="alert-icon"></span>
        <span id="errorMessage"><%= errorMessage != null ? errorMessage : "" %></span>
    </div>

    <!-- Success Alert -->
    <div id="successAlert" class="alert alert-success <%= successMessage != null && !successMessage.isEmpty() ? "show" : "" %>">
        <span class="alert-icon"></span>
        <span id="successMessage"><%= successMessage != null ? successMessage : "" %></span>
    </div>

    <form id="loginForm" action="LoginServlet" method="post" novalidate>
        <div class="form-group">
            <label for="username">Username <span class="required">*</span></label>
            <input type="text" id="username" name="username"
                   placeholder="Enter your username"
                   value="<%= username %>"
                   required autofocus>
            <div class="field-error" id="usernameError">Please enter your username</div>
        </div>

        <div class="form-group">
            <label for="password">Password <span class="required">*</span></label>
            <input type="password" id="password" name="password"
                   placeholder="Enter your password" required>
            <div class="field-error" id="passwordError">Please enter your password</div>
        </div>

        <button type="submit" class="btn-login" id="loginBtn">
            <span id="btnText">Login</span>
        </button>
    </form>

    <div class="footer-links">
        Don't have an account? <a href="register.jsp">Register here</a>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const form = document.getElementById('loginForm');
        const usernameInput = document.getElementById('username');
        const passwordInput = document.getElementById('password');
        const usernameError = document.getElementById('usernameError');
        const passwordError = document.getElementById('passwordError');
        const errorAlert = document.getElementById('errorAlert');
        const successAlert = document.getElementById('successAlert');
        const loginBtn = document.getElementById('loginBtn');
        const btnText = document.getElementById('btnText');

        // Hide alerts on input
        usernameInput.addEventListener('input', function() {
            hideError(usernameInput, usernameError);
            hideAlert(errorAlert);
        });

        passwordInput.addEventListener('input', function() {
            hideError(passwordInput, passwordError);
            hideAlert(errorAlert);
        });

        // Real-time validation
        usernameInput.addEventListener('blur', function() {
            validateField(usernameInput, usernameError, 'Please enter your username');
        });

        passwordInput.addEventListener('blur', function() {
            validateField(passwordInput, passwordError, 'Please enter your password');
        });

        // Form submission
        form.addEventListener('submit', function(e) {
            let isValid = true;

            // Validate username
            if (!validateField(usernameInput, usernameError, 'Please enter your username')) {
                isValid = false;
            }

            // Validate password
            if (!validateField(passwordInput, passwordError, 'Please enter your password')) {
                isValid = false;
            }

            if (!isValid) {
                e.preventDefault();
                // Show first error field
                if (!usernameInput.value.trim()) {
                    usernameInput.focus();
                } else if (!passwordInput.value.trim()) {
                    passwordInput.focus();
                }
                return;
            }

            // Show loading state
            loginBtn.disabled = true;
            btnText.innerHTML = '<span class="spinner"></span> Logging in...';

            // Hide any old alerts
            hideAlert(errorAlert);
            hideAlert(successAlert);
        });

        function validateField(input, errorElement, errorMessage) {
            const value = input.value.trim();
            if (!value) {
                input.classList.add('error');
                errorElement.textContent = errorMessage;
                errorElement.classList.add('show');
                return false;
            } else {
                hideError(input, errorElement);
                return true;
            }
        }

        function hideError(input, errorElement) {
            input.classList.remove('error');
            errorElement.classList.remove('show');
        }

        function hideAlert(alertElement) {
            alertElement.classList.remove('show');
        }

        // Click on error alert to dismiss
        errorAlert.addEventListener('click', function() {
            hideAlert(errorAlert);
        });

        successAlert.addEventListener('click', function() {
            hideAlert(successAlert);
        });
    });

    // Handle server-side error messages with animation
    window.onload = function() {
        const errorAlert = document.getElementById('errorAlert');
        const successAlert = document.getElementById('successAlert');

        if (errorAlert.classList.contains('show')) {
            setTimeout(() => {
                errorAlert.style.opacity = '1';
            }, 100);
        }

        if (successAlert.classList.contains('show')) {
            setTimeout(() => {
                successAlert.style.opacity = '1';
            }, 100);
        }
    };
</script>

</body>
</html>
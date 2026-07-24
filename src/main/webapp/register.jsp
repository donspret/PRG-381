<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    String successMessage = (String) request.getAttribute("successMessage");

    // Get form data for repopulation
    String firstName = (String) request.getAttribute("firstName");
    String lastName = (String) request.getAttribute("lastName");
    String email = (String) request.getAttribute("email");
    String username = (String) request.getAttribute("username");
    String role = (String) request.getAttribute("role");

    if (firstName == null) firstName = "";
    if (lastName == null) lastName = "";
    if (email == null) email = "";
    if (username == null) username = "";
    if (role == null) role = "";
%>

<!DOCTYPE html>
<html>
<head>
    <title>Register - Cleaning Inventory System</title>
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
            max-width: 520px;
            width: 100%;
            background: white;
            padding: 40px 35px;
            border-radius: 12px;
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
            animation: fadeIn 0.5s ease-in;
            max-height: 98vh;
            overflow-y: auto;
        }

        .container::-webkit-scrollbar {
            width: 6px;
        }

        .container::-webkit-scrollbar-track {
            background: #f1f1f1;
            border-radius: 10px;
        }

        .container::-webkit-scrollbar-thumb {
            background: #3498db;
            border-radius: 10px;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .header {
            text-align: center;
            margin-bottom: 25px;
            border-bottom: 2px solid #eee;
            padding-bottom: 18px;
        }

        .header h1 {
            color: #2c3e50;
            font-size: 24px;
            margin-bottom: 3px;
        }

        .header .subtitle {
            color: #7f8c8d;
            font-size: 14px;
        }

        .header .icon {
            font-size: 40px;
            display: block;
            margin-bottom: 8px;
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }

        .form-group {
            margin-bottom: 18px;
        }

        .form-group.full-width {
            grid-column: 1 / -1;
        }

        .form-group label {
            display: block;
            color: #2c3e50;
            font-weight: 600;
            font-size: 13px;
            margin-bottom: 5px;
        }

        .form-group label .required {
            color: #e74c3c;
        }

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 10px 14px;
            border: 2px solid #ddd;
            border-radius: 8px;
            font-size: 14px;
            transition: border-color 0.3s, box-shadow 0.3s;
            background: #fafafa;
            font-family: inherit;
        }

        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: #3498db;
            box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.15);
            background: white;
        }

        .form-group input.error,
        .form-group select.error {
            border-color: #e74c3c;
        }

        .form-group input.error:focus,
        .form-group select.error:focus {
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

        .form-group .hint {
            color: #7f8c8d;
            font-size: 12px;
            margin-top: 4px;
            display: block;
        }

        .form-group .hint.valid {
            color: #27ae60;
        }

        .form-group .hint.invalid {
            color: #e74c3c;
        }

        .password-strength {
            height: 4px;
            background: #eee;
            border-radius: 4px;
            margin-top: 6px;
            overflow: hidden;
            transition: all 0.3s;
        }

        .password-strength .bar {
            height: 100%;
            width: 0%;
            transition: width 0.4s, background 0.4s;
            border-radius: 4px;
        }

        .password-strength .bar.weak {
            width: 25%;
            background: #e74c3c;
        }

        .password-strength .bar.medium {
            width: 50%;
            background: #f39c12;
        }

        .password-strength .bar.strong {
            width: 75%;
            background: #3498db;
        }

        .password-strength .bar.very-strong {
            width: 100%;
            background: #27ae60;
        }

        .btn-register {
            width: 100%;
            padding: 14px;
            background: #27ae60;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s, transform 0.1s;
            margin-top: 5px;
        }

        .btn-register:hover {
            background: #219a52;
        }

        .btn-register:active {
            transform: scale(0.98);
        }

        .btn-register:disabled {
            opacity: 0.6;
            cursor: not-allowed;
        }

        .alert {
            padding: 12px 16px;
            border-radius: 8px;
            margin-bottom: 18px;
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
            margin-top: 22px;
            padding-top: 18px;
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

        @media (max-width: 520px) {
            .container {
                padding: 25px 18px;
            }

            .form-row {
                grid-template-columns: 1fr;
                gap: 0;
            }

            .header h1 {
                font-size: 20px;
            }
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <span class="icon">📝</span>
        <h1>Create Account</h1>
        <p class="subtitle">Register for Cleaning Management System</p>
    </div>

    <!-- Error Alert -->
    <div id="errorAlert" class="alert alert-error <%= errorMessage != null && !errorMessage.isEmpty() ? "show" : "" %>">
        <span class="alert-icon">❌</span>
        <span id="errorMessage"><%= errorMessage != null ? errorMessage : "" %></span>
    </div>

    <!-- Success Alert -->
    <div id="successAlert" class="alert alert-success <%= successMessage != null && !successMessage.isEmpty() ? "show" : "" %>">
        <span class="alert-icon">✅</span>
        <span id="successMessage"><%= successMessage != null ? successMessage : "" %></span>
    </div>

    <form id="registerForm" action="${pageContext.request.contextPath}/RegisterServlet" method="post" novalidate>
        <div class="form-row">
            <div class="form-group">
                <label for="firstName">First Name <span class="required">*</span></label>
                <input type="text" id="firstName" name="firstName"
                       placeholder="Enter first name"
                       value="<%= firstName %>"
                       required autofocus>
                <div class="field-error" id="firstNameError">Please enter your first name</div>
            </div>

            <div class="form-group">
                <label for="lastName">Last Name <span class="required">*</span></label>
                <input type="text" id="lastName" name="lastName"
                       placeholder="Enter last name"
                       value="<%= lastName %>"
                       required>
                <div class="field-error" id="lastNameError">Please enter your last name</div>
            </div>
        </div>

        <div class="form-group">
            <label for="email">Email Address <span class="required">*</span></label>
            <input type="email" id="email" name="email"
                   placeholder="Enter your email"
                   value="<%= email %>"
                   required>
            <div class="field-error" id="emailError">Please enter a valid email address</div>
        </div>

        <div class="form-group">
            <label for="username">Username <span class="required">*</span></label>
            <input type="text" id="username" name="username"
                   placeholder="Choose a username"
                   value="<%= username %>"
                   required>
            <div class="field-error" id="usernameError">Please choose a username</div>
        </div>

        <div class="form-group">
            <label for="password">Password <span class="required">*</span></label>
            <input type="password" id="password" name="password"
                   placeholder="Create a strong password" required>
            <span class="hint" id="passwordHint">8+ chars, uppercase, lowercase, digit, special char (@#$%^&+=!)</span>
            <div class="password-strength">
                <div class="bar" id="passwordStrengthBar"></div>
            </div>
            <div class="field-error" id="passwordError">Password does not meet requirements</div>
        </div>

        <div class="form-group">
            <label for="confirmPassword">Confirm Password <span class="required">*</span></label>
            <input type="password" id="confirmPassword" name="confirmPassword"
                   placeholder="Re-enter your password" required>
            <div class="field-error" id="confirmPasswordError">Passwords do not match</div>
        </div>

        <div class="form-group">
            <label for="role">Role <span class="required">*</span></label>
            <select id="role" name="role" required>
                <option value="">Select Role</option>
                <option value="Storekeeper" <%= role.equals("Storekeeper") ? "selected" : "" %>>Storekeeper</option>
                <option value="Supervisor" <%= role.equals("Supervisor") ? "selected" : "" %>>Supervisor</option>
            </select>
            <div class="field-error" id="roleError">Please select a role</div>
        </div>

        <button type="submit" class="btn-register" id="registerBtn">
            <span id="btnText">✅ Register</span>
        </button>
    </form>

    <div class="footer-links">
        Already have an account? <a href="login.jsp">Sign in here</a>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const form = document.getElementById('registerForm');
        const firstNameInput = document.getElementById('firstName');
        const lastNameInput = document.getElementById('lastName');
        const emailInput = document.getElementById('email');
        const usernameInput = document.getElementById('username');
        const passwordInput = document.getElementById('password');
        const confirmPasswordInput = document.getElementById('confirmPassword');
        const roleSelect = document.getElementById('role');
        const registerBtn = document.getElementById('registerBtn');
        const btnText = document.getElementById('btnText');
        const strengthBar = document.getElementById('passwordStrengthBar');
        const passwordHint = document.getElementById('passwordHint');

        // Validation functions
        const validators = {
            firstName: (val) => val.trim().length >= 2,
            lastName: (val) => val.trim().length >= 2,
            email: (val) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(val),
            username: (val) => val.trim().length >= 3,
            password: (val) => {
                const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=!])[A-Za-z\d@#$%^&+=!]{8,}$/;
                return regex.test(val);
            },
            confirmPassword: (val) => val === passwordInput.value,
            role: (val) => val !== ''
        };

        // Field configurations
        const fields = [
            { input: firstNameInput, error: 'firstNameError', message: 'Please enter your first name' },
            { input: lastNameInput, error: 'lastNameError', message: 'Please enter your last name' },
            { input: emailInput, error: 'emailError', message: 'Please enter a valid email address' },
            { input: usernameInput, error: 'usernameError', message: 'Please choose a username' },
            { input: passwordInput, error: 'passwordError', message: 'Password does not meet requirements' },
            { input: confirmPasswordInput, error: 'confirmPasswordError', message: 'Passwords do not match' },
            { input: roleSelect, error: 'roleError', message: 'Please select a role' }
        ];

        // Real-time validation on input
        fields.forEach(field => {
            field.input.addEventListener('input', function() {
                validateField(field);
            });
            field.input.addEventListener('blur', function() {
                validateField(field);
            });
        });

        // Password strength meter
        passwordInput.addEventListener('input', function() {
            const password = this.value;
            const strength = getPasswordStrength(password);
            updateStrengthBar(strength);
            updatePasswordHint(password);
            validateField({ input: passwordInput, error: 'passwordError', message: 'Password does not meet requirements' });

            // Also validate confirm password when password changes
            if (confirmPasswordInput.value) {
                validateField({ input: confirmPasswordInput, error: 'confirmPasswordError', message: 'Passwords do not match' });
            }
        });

        confirmPasswordInput.addEventListener('input', function() {
            validateField({ input: confirmPasswordInput, error: 'confirmPasswordError', message: 'Passwords do not match' });
        });

        function getPasswordStrength(password) {
            let strength = 0;
            if (password.length >= 8) strength++;
            if (/[a-z]/.test(password)) strength++;
            if (/[A-Z]/.test(password)) strength++;
            if (/\d/.test(password)) strength++;
            if (/[@#$%^&+=!]/.test(password)) strength++;
            return strength;
        }

        function updateStrengthBar(strength) {
            const bar = strengthBar;
            const classes = ['weak', 'medium', 'strong', 'very-strong'];
            bar.className = 'bar';

            if (strength === 0) {
                bar.style.width = '0%';
                return;
            }

            if (strength <= 2) {
                bar.classList.add('weak');
                passwordHint.textContent = 'Weak password - add more variety';
                passwordHint.className = 'hint invalid';
            } else if (strength === 3) {
                bar.classList.add('medium');
                passwordHint.textContent = 'Medium password - consider adding more characters';
                passwordHint.className = 'hint invalid';
            } else if (strength === 4) {
                bar.classList.add('strong');
                passwordHint.textContent = 'Strong password!';
                passwordHint.className = 'hint valid';
            } else if (strength === 5) {
                bar.classList.add('very-strong');
                passwordHint.textContent = 'Very strong password! Excellent!';
                passwordHint.className = 'hint valid';
            }
        }

        function updatePasswordHint(password) {
            if (!password) {
                passwordHint.textContent = '8+ chars, uppercase, lowercase, digit, special char (@#$%^&+=!)';
                passwordHint.className = 'hint';
                return;
            }
            // Strength meter update will handle this
        }

        function validateField(field) {
            const input = field.input;
            const errorElement = document.getElementById(field.error);
            const value = input.value;
            const validator = validators[input.id] || validators[input.name];

            if (!validator || validator(value)) {
                input.classList.remove('error');
                errorElement.classList.remove('show');
                return true;
            } else {
                input.classList.add('error');
                errorElement.textContent = field.message;
                errorElement.classList.add('show');
                return false;
            }
        }

        // Form submission
        form.addEventListener('submit', function(e) {
            let isValid = true;

            // Validate all fields
            fields.forEach(field => {
                if (!validateField(field)) {
                    isValid = false;
                }
            });

            if (!isValid) {
                e.preventDefault();
                // Focus first invalid field
                const firstError = fields.find(f => f.input.classList.contains('error'));
                if (firstError) {
                    firstError.input.focus();
                }
                return;
            }

            // Show loading state
            registerBtn.disabled = true;
            btnText.innerHTML = '<span class="spinner"></span> Creating account...';

            // Hide alerts
            hideAlert(errorAlert);
            hideAlert(successAlert);
        });

        function hideAlert(alertElement) {
            alertElement.classList.remove('show');
        }

        // Click to dismiss alerts
        const errorAlert = document.getElementById('errorAlert');
        const successAlert = document.getElementById('successAlert');

        errorAlert.addEventListener('click', function() { hideAlert(errorAlert); });
        successAlert.addEventListener('click', function() { hideAlert(successAlert); });

        // Reset form state after server error
        if (errorAlert.classList.contains('show') || successAlert.classList.contains('show')) {
            registerBtn.disabled = false;
            btnText.innerHTML = '✅ Register';
        }
    });
</script>

</body>
</html>
package com.cleaninginventory.util;

import java.util.regex.Pattern;


    public class PasswordValidator {

        // Password must be at least 8 characters, contain uppercase, lowercase, digit, and special character
        private static final String PASSWORD_PATTERN =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

        private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

        public static boolean isValid(String password) {
            if (password == null) return false;
            return pattern.matcher(password).matches();
        }

        public static String getPasswordRequirements() {
            return "Password must contain:\n" +
                    "- At least 8 characters\n" +
                    "- At least one uppercase letter\n" +
                    "- At least one lowercase letter\n" +
                    "- At least one digit\n" +
                    "- At least one special character (@#$%^&+=!)\n" +
                    "- No spaces allowed";
        }

        public static String validatePassword(String password) {
            if (password == null || password.isEmpty()) {
                return "Password cannot be empty.";
            }
            if (password.length() < 8) {
                return "Password must be at least 8 characters long.";
            }
            if (!password.matches(".*[A-Z].*")) {
                return "Password must contain at least one uppercase letter.";
            }
            if (!password.matches(".*[a-z].*")) {
                return "Password must contain at least one lowercase letter.";
            }
            if (!password.matches(".*[0-9].*")) {
                return "Password must contain at least one digit.";
            }
            if (!password.matches(".*[@#$%^&+=!].*")) {
                return "Password must contain at least one special character (@#$%^&+=!).";
            }
            if (password.contains(" ")) {
                return "Password cannot contain spaces.";
            }
            return null; // Password is valid
        }
    }


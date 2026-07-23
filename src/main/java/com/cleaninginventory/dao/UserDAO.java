package com.cleaninginventory.dao;

import com.cleaninginventory.model.User;
import com.cleaninginventory.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // REGISTER USER - Returns String for better error messages
    public String registerUser(User user) {

        // Check if username exists
        if (usernameExists(user.getUsername())) {
            return "Username already exists. Please choose another.";
        }

        // Check if email exists
        if (emailExists(user.getEmail())) {
            return "Email already registered. Please use another email.";
        }

        String sql = "INSERT INTO users "
                + "(first_name, last_name, email, username, password, role) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getRole());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                return "SUCCESS";
            } else {
                return "Registration failed. Please try again.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error: " + e.getMessage();
        }
    }

    // LOGIN USER - Checks is_active
    public User loginUser(String username, String password) {

        User user = null;

        // Added is_active = true check
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND is_active = true";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                user = new User();
                user.setUserId(result.getInt("user_id"));
                user.setFirstName(result.getString("first_name"));
                user.setLastName(result.getString("last_name"));
                user.setEmail(result.getString("email"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                user.setRole(result.getString("role"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // NEW: Check if username exists
    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // NEW: Check if email exists
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // NEW: Get user by username (optional)
    public User getUserByUsername(String username) {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                user = new User();
                user.setUserId(result.getInt("user_id"));
                user.setFirstName(result.getString("first_name"));
                user.setLastName(result.getString("last_name"));
                user.setEmail(result.getString("email"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                user.setRole(result.getString("role"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // NEW: Get user by email (optional)
    public User getUserByEmail(String email) {
        User user = null;
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                user = new User();
                user.setUserId(result.getInt("user_id"));
                user.setFirstName(result.getString("first_name"));
                user.setLastName(result.getString("last_name"));
                user.setEmail(result.getString("email"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                user.setRole(result.getString("role"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}

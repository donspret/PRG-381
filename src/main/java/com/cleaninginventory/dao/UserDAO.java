package com.cleaninginventory.dao;

import com.cleaninginventory.model.User;
import com.cleaninginventory.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO {

    public boolean registerUser(User user) {


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

            return rowsInserted > 0;


        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }
    public User loginUser(String username, String password) {

        User user = null;

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";


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
}

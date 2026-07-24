package com.cleaninginventory.dao;

import com.cleaninginventory.model.Cleaner;
import com.cleaninginventory.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CleanerDAO {

    public boolean addCleaner(Cleaner cleaner) {
        String sql = "INSERT INTO cleaners (first_name, last_name, email, phone, address, " +
                "department, status, shift, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (conn == null) return false;

            pstmt.setString(1, cleaner.getFirstName());
            pstmt.setString(2, cleaner.getLastName());
            pstmt.setString(3, cleaner.getEmail());
            pstmt.setString(4, cleaner.getPhone());
            pstmt.setString(5, cleaner.getAddress());
            pstmt.setString(6, cleaner.getDepartment());
            pstmt.setString(7, cleaner.getStatus());
            pstmt.setString(8, cleaner.getShift());
            pstmt.setString(9, cleaner.getNotes());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        cleaner.setCleanerId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cleaner getCleanerById(int cleanerId) {
        String sql = "SELECT * FROM cleaners WHERE cleaner_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return null;

            pstmt.setInt(1, cleanerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToCleaner(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Cleaner> getAllCleaners() {
        List<Cleaner> cleaners = new ArrayList<>();
        String sql = "SELECT * FROM cleaners ORDER BY last_name, first_name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) return cleaners;

            while (rs.next()) {
                cleaners.add(mapResultSetToCleaner(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cleaners;
    }

    public List<Cleaner> searchCleaners(String keyword) {
        List<Cleaner> cleaners = new ArrayList<>();
        String sql = "SELECT * FROM cleaners WHERE first_name ILIKE ? OR last_name ILIKE ? OR email ILIKE ? OR department ILIKE ? " +
                "ORDER BY last_name, first_name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return cleaners;

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                cleaners.add(mapResultSetToCleaner(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cleaners;
    }

    public boolean updateCleaner(Cleaner cleaner) {
        String sql = "UPDATE cleaners SET first_name = ?, last_name = ?, email = ?, phone = ?, address = ?, " +
                "department = ?, status = ?, shift = ?, notes = ? " +
                "WHERE cleaner_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            pstmt.setString(1, cleaner.getFirstName());
            pstmt.setString(2, cleaner.getLastName());
            pstmt.setString(3, cleaner.getEmail());
            pstmt.setString(4, cleaner.getPhone());
            pstmt.setString(5, cleaner.getAddress());
            pstmt.setString(6, cleaner.getDepartment());
            pstmt.setString(7, cleaner.getStatus());
            pstmt.setString(8, cleaner.getShift());
            pstmt.setString(9, cleaner.getNotes());
            pstmt.setInt(10, cleaner.getCleanerId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCleaner(int cleanerId) {
        String sql = "DELETE FROM cleaners WHERE cleaner_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            pstmt.setInt(1, cleanerId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Cleaner mapResultSetToCleaner(ResultSet rs) throws SQLException {
        Cleaner cleaner = new Cleaner();
        cleaner.setCleanerId(rs.getInt("cleaner_id"));
        cleaner.setFirstName(rs.getString("first_name"));
        cleaner.setLastName(rs.getString("last_name"));
        cleaner.setEmail(rs.getString("email"));
        cleaner.setPhone(rs.getString("phone"));
        cleaner.setAddress(rs.getString("address"));
        cleaner.setDepartment(rs.getString("department"));
        cleaner.setStatus(rs.getString("status"));
        cleaner.setShift(rs.getString("shift"));
        cleaner.setNotes(rs.getString("notes"));
        return cleaner;
    }

    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM cleaners WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return true;

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Cleaner> getCleanersByDepartment(String department) {
        List<Cleaner> cleaners = new ArrayList<>();
        String sql = "SELECT * FROM cleaners WHERE department = ? AND status = 'Active' ORDER BY last_name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return cleaners;

            pstmt.setString(1, department);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                cleaners.add(mapResultSetToCleaner(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cleaners;
    }

    public List<String> getDepartments() {
        List<String> departments = new ArrayList<>();
        String sql = "SELECT DISTINCT department FROM cleaners WHERE department IS NOT NULL ORDER BY department";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) return departments;

            while (rs.next()) {
                departments.add(rs.getString("department"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }
}

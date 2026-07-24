package com.cleaninginventory.dao;

import com.cleaninginventory.model.Issue;
import com.cleaninginventory.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IssueDAO {

    public boolean addIssue(Issue issue) {
        String sql = "INSERT INTO issues (issue_type, description, severity, material_id, " +
                "supplier_id, storekeeper_id, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (conn == null) {
                System.err.println("❌ Connection is null in addIssue");
                return false;
            }

            pstmt.setString(1, issue.getIssueType());
            pstmt.setString(2, issue.getDescription());
            pstmt.setString(3, issue.getSeverity());
            pstmt.setInt(4, issue.getMaterialId());
            pstmt.setInt(5, issue.getSupplierId());
            pstmt.setInt(6, issue.getStorekeeperId());
            pstmt.setString(7, Issue.STATUS_OPEN);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        issue.setIssueId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("❌ Error adding issue: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Issue getIssueById(int issueId) {
        String sql = "SELECT i.*, m.material_name, s.company_name as supplier_name, " +
                "u.first_name || ' ' || u.last_name as storekeeper_name, " +
                "r.first_name || ' ' || r.last_name as resolved_by_name " +
                "FROM issues i " +
                "LEFT JOIN materials m ON i.material_id = m.material_id " +
                "LEFT JOIN suppliers s ON i.supplier_id = s.supplier_id " +
                "LEFT JOIN users u ON i.storekeeper_id = u.user_id " +
                "LEFT JOIN users r ON i.resolved_by = r.user_id " +
                "WHERE i.issue_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return null;

            pstmt.setInt(1, issueId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToIssue(rs);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error getting issue by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Issue> getAllIssues() {
        List<Issue> issues = new ArrayList<>();
        String sql = "SELECT i.*, m.material_name, s.company_name as supplier_name, " +
                "u.first_name || ' ' || u.last_name as storekeeper_name, " +
                "r.first_name || ' ' || r.last_name as resolved_by_name " +
                "FROM issues i " +
                "LEFT JOIN materials m ON i.material_id = m.material_id " +
                "LEFT JOIN suppliers s ON i.supplier_id = s.supplier_id " +
                "LEFT JOIN users u ON i.storekeeper_id = u.user_id " +
                "LEFT JOIN users r ON i.resolved_by = r.user_id " +
                "ORDER BY i.created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) return issues;

            while (rs.next()) {
                issues.add(mapResultSetToIssue(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error getting all issues: " + e.getMessage());
            e.printStackTrace();
        }
        return issues;
    }

    public List<Issue> getIssuesByStorekeeper(int storekeeperId) {
        List<Issue> issues = new ArrayList<>();
        String sql = "SELECT i.*, m.material_name, s.company_name as supplier_name, " +
                "u.first_name || ' ' || u.last_name as storekeeper_name, " +
                "r.first_name || ' ' || r.last_name as resolved_by_name " +
                "FROM issues i " +
                "LEFT JOIN materials m ON i.material_id = m.material_id " +
                "LEFT JOIN suppliers s ON i.supplier_id = s.supplier_id " +
                "LEFT JOIN users u ON i.storekeeper_id = u.user_id " +
                "LEFT JOIN users r ON i.resolved_by = r.user_id " +
                "WHERE i.storekeeper_id = ? " +
                "ORDER BY i.created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return issues;

            pstmt.setInt(1, storekeeperId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                issues.add(mapResultSetToIssue(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error getting issues by storekeeper: " + e.getMessage());
            e.printStackTrace();
        }
        return issues;
    }

    public List<Issue> getIssuesByStatus(String status) {
        List<Issue> issues = new ArrayList<>();
        String sql = "SELECT i.*, m.material_name, s.company_name as supplier_name, " +
                "u.first_name || ' ' || u.last_name as storekeeper_name, " +
                "r.first_name || ' ' || r.last_name as resolved_by_name " +
                "FROM issues i " +
                "LEFT JOIN materials m ON i.material_id = m.material_id " +
                "LEFT JOIN suppliers s ON i.supplier_id = s.supplier_id " +
                "LEFT JOIN users u ON i.storekeeper_id = u.user_id " +
                "LEFT JOIN users r ON i.resolved_by = r.user_id " +
                "WHERE i.status = ? " +
                "ORDER BY i.created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return issues;

            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                issues.add(mapResultSetToIssue(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error getting issues by status: " + e.getMessage());
            e.printStackTrace();
        }
        return issues;
    }

    public boolean updateIssueStatus(int issueId, String status) {
        String sql = "UPDATE issues SET status = ? WHERE issue_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            pstmt.setString(1, status);
            pstmt.setInt(2, issueId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error updating issue status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean resolveIssue(int issueId, String resolution, int resolvedBy) {
        String sql = "UPDATE issues SET status = ?, resolution = ?, resolved_by = ?, " +
                "resolved_at = CURRENT_TIMESTAMP WHERE issue_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            pstmt.setString(1, Issue.STATUS_RESOLVED);
            pstmt.setString(2, resolution);
            pstmt.setInt(3, resolvedBy);
            pstmt.setInt(4, issueId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error resolving issue: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    private Issue mapResultSetToIssue(ResultSet rs) throws SQLException {
        Issue issue = new Issue();
        issue.setIssueId(rs.getInt("issue_id"));
        issue.setIssueType(rs.getString("issue_type"));
        issue.setDescription(rs.getString("description"));
        issue.setSeverity(rs.getString("severity"));
        issue.setMaterialId(rs.getInt("material_id"));
        issue.setMaterialName(rs.getString("material_name"));
        issue.setSupplierId(rs.getInt("supplier_id"));
        issue.setSupplierName(rs.getString("supplier_name"));
        issue.setStorekeeperId(rs.getInt("storekeeper_id"));
        issue.setStorekeeperName(rs.getString("storekeeper_name"));
        issue.setStatus(rs.getString("status"));
        issue.setResolution(rs.getString("resolution"));
        issue.setResolvedBy(rs.getInt("resolved_by"));
        issue.setResolvedByName(rs.getString("resolved_by_name"));
        issue.setCreatedAt(rs.getTimestamp("created_at"));
        issue.setResolvedAt(rs.getTimestamp("resolved_at"));
        return issue;
    }
}
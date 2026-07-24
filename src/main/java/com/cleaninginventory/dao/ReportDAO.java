package com.cleaninginventory.dao;

import com.cleaninginventory.model.Report;
import com.cleaninginventory.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    public List<Report.InventoryItem> getInventoryReport() {
        List<Report.InventoryItem> items = new ArrayList<>();
        String sql = "SELECT material_id, material_name, category, quantity_in_stock, " +
                "low_stock_threshold, unit, " +
                "CASE WHEN quantity_in_stock <= low_stock_threshold THEN 'Low Stock' " +
                "ELSE 'In Stock' END as status " +
                "FROM materials ORDER BY material_name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) {
                System.err.println("❌ Connection is null in getInventoryReport");
                return items;
            }

            while (rs.next()) {
                Report.InventoryItem item = new Report.InventoryItem();
                item.setMaterialId(rs.getInt("material_id"));
                item.setMaterialName(rs.getString("material_name"));
                item.setCategory(rs.getString("category"));
                item.setQuantityInStock(rs.getInt("quantity_in_stock"));
                item.setLowStockThreshold(rs.getInt("low_stock_threshold"));
                item.setUnit(rs.getString("unit"));
                item.setStatus(rs.getString("status"));
                items.add(item);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error getting inventory report: " + e.getMessage());
            e.printStackTrace();
        }
        return items;
    }

    public List<Report.InventoryItem> getLowStockReport() {
        List<Report.InventoryItem> items = new ArrayList<>();
        String sql = "SELECT material_id, material_name, category, quantity_in_stock, " +
                "low_stock_threshold, unit " +
                "FROM materials WHERE quantity_in_stock <= low_stock_threshold " +
                "ORDER BY quantity_in_stock ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) {
                System.err.println("❌ Connection is null in getLowStockReport");
                return items;
            }

            while (rs.next()) {
                Report.InventoryItem item = new Report.InventoryItem();
                item.setMaterialId(rs.getInt("material_id"));
                item.setMaterialName(rs.getString("material_name"));
                item.setCategory(rs.getString("category"));
                item.setQuantityInStock(rs.getInt("quantity_in_stock"));
                item.setLowStockThreshold(rs.getInt("low_stock_threshold"));
                item.setUnit(rs.getString("unit"));
                item.setStatus("Low Stock");
                items.add(item);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error getting low stock report: " + e.getMessage());
            e.printStackTrace();
        }
        return items;
    }

    public List<Report.IssuanceItem> getIssuanceHistory() {
        List<Report.IssuanceItem> items = new ArrayList<>();
        String sql = "SELECT i.issuance_id, m.material_name, " +
                "c.first_name || ' ' || c.last_name as cleaner_name, " +
                "i.quantity, i.issue_date, i.status " +
                "FROM issuance i " +
                "LEFT JOIN materials m ON i.material_id = m.material_id " +
                "LEFT JOIN cleaners c ON i.cleaner_id = c.cleaner_id " +
                "ORDER BY i.issue_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) {
                System.err.println("❌ Connection is null in getIssuanceHistory");
                return items;
            }

            while (rs.next()) {
                Report.IssuanceItem item = new Report.IssuanceItem();
                item.setIssuanceId(rs.getInt("issuance_id"));
                item.setMaterialName(rs.getString("material_name"));
                item.setCleanerName(rs.getString("cleaner_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setIssueDate(rs.getTimestamp("issue_date"));
                item.setStatus(rs.getString("status"));
                items.add(item);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error getting issuance history: " + e.getMessage());
            e.printStackTrace();
        }
        return items;
    }

    public List<Report.MaterialUsageItem> getMaterialUsageReport() {
        List<Report.MaterialUsageItem> items = new ArrayList<>();
        String sql = "SELECT m.material_name, " +
                "COALESCE(SUM(i.quantity), 0) as total_issued, " +
                "m.quantity_in_stock as remaining_stock, " +
                "m.unit " +
                "FROM materials m " +
                "LEFT JOIN issuance i ON m.material_id = i.material_id " +
                "GROUP BY m.material_id, m.material_name, m.quantity_in_stock, m.unit " +
                "ORDER BY total_issued DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) {
                System.err.println("❌ Connection is null in getMaterialUsageReport");
                return items;
            }

            while (rs.next()) {
                Report.MaterialUsageItem item = new Report.MaterialUsageItem();
                item.setMaterialName(rs.getString("material_name"));
                item.setTotalIssued(rs.getInt("total_issued"));
                item.setRemainingStock(rs.getInt("remaining_stock"));
                item.setUnit(rs.getString("unit"));
                items.add(item);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error getting material usage report: " + e.getMessage());
            e.printStackTrace();
        }
        return items;
    }

    public int getTotalIssuedCount() {
        String sql = "SELECT COUNT(*) FROM issuance";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (conn == null) return 0;
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getOpenIssuesCount() {
        String sql = "SELECT COUNT(*) FROM issues WHERE status = 'Open'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (conn == null) return 0;
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

package com.example.final_prg381.dao;

import com.example.final_prg381.model.Cleaner;
import com.example.final_prg381.model.Material;
import com.example.final_prg381.model.StockIssuance;
import com.example.final_prg381.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockIssuanceDAO {

    /**
     * Custom exception so the servlet can show a friendly message
     * instead of a raw SQL error when business rules are broken.
     */
    public static class InsufficientStockException extends Exception {
        public InsufficientStockException(String message) {
            super(message);
        }
    }

    /**
     * Issues stock to a cleaner: validates quantity, checks available
     * stock, deducts it, and records the issuance - all in one
     * transaction (commit only if every step succeeds).
     */
    public void issueStock(int materialId, int cleanerId, int quantityRequested, String issuedBy)
            throws SQLException, InsufficientStockException {

        if (quantityRequested <= 0) {
            throw new InsufficientStockException("Quantity issued must be greater than zero.");
        }

        String checkStockSql = "SELECT quantity, name FROM materials WHERE material_id = ? FOR UPDATE";
        String updateStockSql = "UPDATE materials SET quantity = quantity - ? WHERE material_id = ?";
        String insertIssuanceSql = "INSERT INTO stock_issuance (material_id, cleaner_id, quantity_issued, issued_by) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement checkStmt = conn.prepareStatement(checkStockSql)) {
                checkStmt.setInt(1, materialId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        throw new InsufficientStockException("Material not found.");
                    }

                    int available = rs.getInt("quantity");
                    String materialName = rs.getString("name");

                    if (quantityRequested > available) {
                        conn.rollback();
                        throw new InsufficientStockException(
                                "Cannot issue " + quantityRequested +
                                        " units of \"" + materialName +
                                        "\" - only " + available +
                                        " available in stock.");
                    }
                }
            }

            try (PreparedStatement updateStmt = conn.prepareStatement(updateStockSql)) {
                updateStmt.setInt(1, quantityRequested);
                updateStmt.setInt(2, materialId);
                updateStmt.executeUpdate();
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertIssuanceSql)) {
                insertStmt.setInt(1, materialId);
                insertStmt.setInt(2, cleanerId);
                insertStmt.setInt(3, quantityRequested);
                insertStmt.setString(4, issuedBy);
                insertStmt.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            throw e;
        }
    }

    /** Full issuance history */
    public List<StockIssuance> getIssuanceHistory() throws SQLException {

        String sql =
                "SELECT si.issuance_id, si.material_id, m.name AS material_name, " +
                "si.cleaner_id, c.first_name, c.last_name, " +
                "si.quantity_issued, si.issued_date, si.issued_by " +
                "FROM stock_issuance si " +
                "JOIN materials m ON si.material_id = m.material_id " +
                "JOIN cleaners c ON si.cleaner_id = c.cleaner_id " +
                "ORDER BY si.issued_date DESC";

        List<StockIssuance> history = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                history.add(mapRowToIssuance(rs));
            }
        }

        return history;
    }

    /** Inventory report */
    public List<Material> getInventoryReport() throws SQLException {

        String sql =
                "SELECT material_id, name, quantity, reorder_level, unit " +
                "FROM materials ORDER BY name";

        List<Material> materials = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                materials.add(new Material(
                        rs.getInt("material_id"),
                        rs.getString("name"),
                        "",
                        rs.getString("unit"),
                        rs.getInt("quantity"),
                        rs.getInt("reorder_level"),
                        null,
                        0
                ));
            }
        }

        return materials;
    }

    /** Low stock report */
    public List<Material> getLowStockReport() throws SQLException {

        String sql =
                "SELECT material_id, name, quantity, reorder_level, unit " +
                "FROM materials " +
                "WHERE quantity <= reorder_level " +
                "ORDER BY quantity ASC";

        List<Material> materials = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                materials.add(new Material(
                        rs.getInt("material_id"),
                        rs.getString("name"),
                        "",
                        rs.getString("unit"),
                        rs.getInt("quantity"),
                        rs.getInt("reorder_level"),
                        null,
                        0
                ));
            }
        }

        return materials;
    }

    /** Material usage report */
    public List<Object[]> getMaterialUsageReport() throws SQLException {

        String sql =
                "SELECT m.name, SUM(si.quantity_issued) AS total_issued " +
                "FROM stock_issuance si " +
                "JOIN materials m ON si.material_id = m.material_id " +
                "GROUP BY m.name " +
                "ORDER BY total_issued DESC";

        List<Object[]> usage = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usage.add(new Object[]{
                        rs.getString("name"),
                        rs.getInt("total_issued")
                });
            }
        }

        return usage;
    }

    /** Materials dropdown */
    public List<Material> getAllMaterials() throws SQLException {

        String sql =
                "SELECT material_id, name, quantity, reorder_level, unit " +
                "FROM materials ORDER BY name";

        List<Material> materials = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                materials.add(new Material(
                        rs.getInt("material_id"),
                        rs.getString("name"),
                        "",
                        rs.getString("unit"),
                        rs.getInt("quantity"),
                        rs.getInt("reorder_level"),
                        null,
                        0
                ));
            }
        }

        return materials;
    }

    /** Cleaners dropdown */
    public List<Cleaner> getAllCleaners() throws SQLException {

        String sql =
                "SELECT cleaner_id, first_name, last_name, department " +
                "FROM cleaners ORDER BY first_name";

        List<Cleaner> cleaners = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                cleaners.add(new Cleaner(
                        rs.getInt("cleaner_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("department")
                ));
            }
        }

        return cleaners;
    }

    private StockIssuance mapRowToIssuance(ResultSet rs) throws SQLException {

        StockIssuance issuance = new StockIssuance();

        issuance.setIssuanceId(rs.getInt("issuance_id"));
        issuance.setMaterialId(rs.getInt("material_id"));
        issuance.setMaterialName(rs.getString("material_name"));
        issuance.setCleanerId(rs.getInt("cleaner_id"));
        issuance.setCleanerName(
                rs.getString("first_name") + " " +
                rs.getString("last_name")
        );
        issuance.setQuantityIssued(rs.getInt("quantity_issued"));
        issuance.setIssuedDate(rs.getTimestamp("issued_date"));
        issuance.setIssuedBy(rs.getString("issued_by"));

        return issuance;
    }
}
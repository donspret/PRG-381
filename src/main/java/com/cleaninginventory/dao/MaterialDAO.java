package com.cleaninginventory.dao;

import com.cleaninginventory.model.Material;
import com.cleaninginventory.util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    public boolean addMaterial(Material material) {
        String sql = "INSERT INTO materials (material_name, category, unit, quantity_in_stock, " +
                "low_stock_threshold, unit_price, supplier_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (conn == null) {
                System.err.println("❌ Connection is null in addMaterial");
                return false;
            }

            pstmt.setString(1, material.getMaterialName());
            pstmt.setString(2, material.getCategory());
            pstmt.setString(3, material.getUnit());
            pstmt.setInt(4, material.getQuantityInStock());
            pstmt.setInt(5, material.getLowStockThreshold());
            pstmt.setBigDecimal(6, material.getUnitPrice());
            pstmt.setInt(7, material.getSupplierId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        material.setMaterialId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("❌ Error adding material: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Material getMaterialById(int materialId) {
        String sql = "SELECT * FROM materials WHERE material_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.err.println("❌ Connection is null in getMaterialById");
                return null;
            }

            pstmt.setInt(1, materialId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToMaterial(rs);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error getting material by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM materials ORDER BY material_name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) {
                System.err.println("❌ Connection is null in getAllMaterials");
                return materials;
            }

            while (rs.next()) {
                materials.add(mapResultSetToMaterial(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error getting all materials: " + e.getMessage());
            e.printStackTrace();
        }
        return materials;
    }

    public List<Material> getLowStockMaterials() {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM materials WHERE quantity_in_stock <= low_stock_threshold " +
                "ORDER BY quantity_in_stock ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) {
                System.err.println("❌ Connection is null in getLowStockMaterials");
                return materials;
            }

            while (rs.next()) {
                materials.add(mapResultSetToMaterial(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error getting low stock materials: " + e.getMessage());
            e.printStackTrace();
        }
        return materials;
    }

    public List<Material> searchMaterials(String keyword) {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM materials WHERE material_name ILIKE ? OR category ILIKE ? " +
                "ORDER BY material_name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.err.println("❌ Connection is null in searchMaterials");
                return materials;
            }

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                materials.add(mapResultSetToMaterial(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error searching materials: " + e.getMessage());
            e.printStackTrace();
        }
        return materials;
    }

    public List<Material> getMaterialsBySupplier(int supplierId) {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM materials WHERE supplier_id = ? ORDER BY material_name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.err.println("❌ Connection is null in getMaterialsBySupplier");
                return materials;
            }

            pstmt.setInt(1, supplierId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                materials.add(mapResultSetToMaterial(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error getting materials by supplier: " + e.getMessage());
            e.printStackTrace();
        }
        return materials;
    }

    public boolean updateMaterial(Material material) {
        String sql = "UPDATE materials SET material_name = ?, category = ?, unit = ?, " +
                "quantity_in_stock = ?, low_stock_threshold = ?, unit_price = ?, supplier_id = ? " +
                "WHERE material_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.err.println("❌ Connection is null in updateMaterial");
                return false;
            }

            pstmt.setString(1, material.getMaterialName());
            pstmt.setString(2, material.getCategory());
            pstmt.setString(3, material.getUnit());
            pstmt.setInt(4, material.getQuantityInStock());
            pstmt.setInt(5, material.getLowStockThreshold());
            pstmt.setBigDecimal(6, material.getUnitPrice());
            pstmt.setInt(7, material.getSupplierId());
            pstmt.setInt(8, material.getMaterialId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error updating material: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMaterial(int materialId) {
        String sql = "DELETE FROM materials WHERE material_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.err.println("❌ Connection is null in deleteMaterial");
                return false;
            }

            pstmt.setInt(1, materialId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error deleting material: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private Material mapResultSetToMaterial(ResultSet rs) throws SQLException {
        Material material = new Material();
        material.setMaterialId(rs.getInt("material_id"));
        material.setMaterialName(rs.getString("material_name"));
        material.setCategory(rs.getString("category"));
        material.setUnit(rs.getString("unit"));
        material.setQuantityInStock(rs.getInt("quantity_in_stock"));
        material.setLowStockThreshold(rs.getInt("low_stock_threshold"));
        material.setUnitPrice(rs.getBigDecimal("unit_price"));
        material.setSupplierId(rs.getInt("supplier_id"));
        material.setLastUpdated(rs.getTimestamp("last_updated"));
        material.setCreatedAt(rs.getTimestamp("created_at"));
        return material;
    }
}
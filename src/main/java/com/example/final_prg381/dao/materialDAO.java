package com.example.final_prg381.dao;

import com.example.final_prg381.model.Material;
import com.example.final_prg381.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class materialDAO {

    // validation
    public String validate(Material m) {
        if (m.getMaterialName() == null || m.getMaterialName().trim().isEmpty()) {
            return "Material name is required.";
        }
        if (m.getCategory() == null || m.getCategory().trim().isEmpty()) {
            return "Category is required.";
        }
        if (m.getUnit() == null || m.getUnit().trim().isEmpty()) {
            return "Unit is required.";
        }
        if (m.getQuantityInStock() < 0) {
            return "Quantity in stock cannot be negative.";
        }
        if (m.getLowStockThreshold() < 0) {
            return "Low stock threshold cannot be negative.";
        }
        if (m.getUnitPrice() == null || m.getUnitPrice().signum() <= 0) {
            return "Unit price must be greater than zero.";
        }
        if (m.getSupplierId() <= 0) {
            return "A valid supplier must be selected.";
        }
        return null;
    }

    // add materials
    public boolean addMaterial(Material m) {
        String sql = "INSERT INTO materials (material_name, category, unit, quantity_in_stock, " +
                "low_stock_threshold, unit_price, supplier_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getMaterialName());
            ps.setString(2, m.getCategory());
            ps.setString(3, m.getUnit());
            ps.setInt(4, m.getQuantityInStock());
            ps.setInt(5, m.getLowStockThreshold());
            ps.setBigDecimal(6, m.getUnitPrice());
            ps.setInt(7, m.getSupplierId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // view all materials
    public List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM materials ORDER BY material_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                materials.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    // view single material with specific id
    public Material getMaterialById(int id) {
        String sql = "SELECT * FROM materials WHERE material_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // update materials
    public boolean updateMaterial(Material m) {
        String sql = "UPDATE materials SET material_name=?, category=?, unit=?, quantity_in_stock=?, " +
                "low_stock_threshold=?, unit_price=?, supplier_id=?, last_updated=CURRENT_TIMESTAMP " +
                "WHERE material_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getMaterialName());
            ps.setString(2, m.getCategory());
            ps.setString(3, m.getUnit());
            ps.setInt(4, m.getQuantityInStock());
            ps.setInt(5, m.getLowStockThreshold());
            ps.setBigDecimal(6, m.getUnitPrice());
            ps.setInt(7, m.getSupplierId());
            ps.setInt(8, m.getMaterialId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // delete
    public boolean deleteMaterial(int id) {
        String sql = "DELETE FROM materials WHERE material_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // search/filter
    public List<Material> searchMaterials(String keyword, String category) {
        List<Material> materials = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM materials WHERE 1=1");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND material_name ILIKE ?");
        }
        if (category != null && !category.trim().isEmpty()) {
            sql.append(" AND category = ?");
        }
        sql.append(" ORDER BY material_id");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword.trim() + "%");
            }
            if (category != null && !category.trim().isEmpty()) {
                ps.setString(paramIndex++, category.trim());
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                materials.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    // low stock alerts
    public List<Material> getLowStockMaterials() {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM materials WHERE quantity_in_stock <= low_stock_threshold " +
                "ORDER BY quantity_in_stock ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                materials.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    // converts one database row into a Material object
    private Material mapRow(ResultSet rs) throws SQLException {
        Material m = new Material();
        m.setMaterialId(rs.getInt("material_id"));
        m.setMaterialName(rs.getString("material_name"));
        m.setCategory(rs.getString("category"));
        m.setUnit(rs.getString("unit"));
        m.setQuantityInStock(rs.getInt("quantity_in_stock"));
        m.setLowStockThreshold(rs.getInt("low_stock_threshold"));
        m.setUnitPrice(rs.getBigDecimal("unit_price"));
        m.setSupplierId(rs.getInt("supplier_id"));
        m.setLastUpdated(rs.getTimestamp("last_updated"));
        return m;
    }
}



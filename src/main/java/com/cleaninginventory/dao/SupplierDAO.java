package com.cleaninginventory.dao;

import com.cleaninginventory.model.Supplier;
import com.cleaninginventory.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public boolean addSupplier(Supplier supplier) {
        String sql = "INSERT INTO suppliers (company_name, contact_person, email, phone, " +
                "address, city, postal_code, country, status, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (conn == null) return false;

            pstmt.setString(1, supplier.getCompanyName());
            pstmt.setString(2, supplier.getContactPerson());
            pstmt.setString(3, supplier.getEmail());
            pstmt.setString(4, supplier.getPhone());
            pstmt.setString(5, supplier.getAddress());
            pstmt.setString(6, supplier.getCity());
            pstmt.setString(7, supplier.getPostalCode());
            pstmt.setString(8, supplier.getCountry());
            pstmt.setString(9, supplier.getStatus());
            pstmt.setString(10, supplier.getNotes());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        supplier.setSupplierId(rs.getInt(1));
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

    public Supplier getSupplierById(int supplierId) {
        String sql = "SELECT * FROM suppliers WHERE supplier_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return null;

            pstmt.setInt(1, supplierId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToSupplier(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers ORDER BY company_name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) return suppliers;

            while (rs.next()) {
                suppliers.add(mapResultSetToSupplier(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    public List<Supplier> searchSuppliers(String keyword) {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers WHERE company_name ILIKE ? OR contact_person ILIKE ? OR email ILIKE ? " +
                "ORDER BY company_name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return suppliers;

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                suppliers.add(mapResultSetToSupplier(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    public boolean updateSupplier(Supplier supplier) {
        String sql = "UPDATE suppliers SET company_name = ?, contact_person = ?, email = ?, phone = ?, " +
                "address = ?, city = ?, postal_code = ?, country = ?, status = ?, notes = ? " +
                "WHERE supplier_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            pstmt.setString(1, supplier.getCompanyName());
            pstmt.setString(2, supplier.getContactPerson());
            pstmt.setString(3, supplier.getEmail());
            pstmt.setString(4, supplier.getPhone());
            pstmt.setString(5, supplier.getAddress());
            pstmt.setString(6, supplier.getCity());
            pstmt.setString(7, supplier.getPostalCode());
            pstmt.setString(8, supplier.getCountry());
            pstmt.setString(9, supplier.getStatus());
            pstmt.setString(10, supplier.getNotes());
            pstmt.setInt(11, supplier.getSupplierId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSupplier(int supplierId) {
        String sql = "DELETE FROM suppliers WHERE supplier_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            pstmt.setInt(1, supplierId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Supplier mapResultSetToSupplier(ResultSet rs) throws SQLException {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(rs.getInt("supplier_id"));
        supplier.setCompanyName(rs.getString("company_name"));
        supplier.setContactPerson(rs.getString("contact_person"));
        supplier.setEmail(rs.getString("email"));
        supplier.setPhone(rs.getString("phone"));
        supplier.setAddress(rs.getString("address"));
        supplier.setCity(rs.getString("city"));
        supplier.setPostalCode(rs.getString("postal_code"));
        supplier.setCountry(rs.getString("country"));
        supplier.setStatus(rs.getString("status"));
        supplier.setNotes(rs.getString("notes"));
        return supplier;
    }

    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM suppliers WHERE email = ?";

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
}

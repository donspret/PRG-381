package com.cleaninginventory.dao;

import com.cleaninginventory.model.DashboardStats;
import com.cleaninginventory.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAO {

    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();

        stats.setTotalMaterials(0);
        stats.setLowStockItems(0);
        stats.setTotalCleaners(0);
        stats.setTotalSuppliers(0);
        stats.setRecentIssuances(0);
        stats.setTotalUsers(0);

        try (Connection conn = DBConnection.getConnection()) {

            if (conn == null) {
                System.err.println("❌ Connection is null in DashboardDAO");
                return stats;
            }

            System.out.println("✅ DashboardDAO: Connection obtained");

            try {
                String materialSql = "SELECT COUNT(*) FROM materials";
                try (PreparedStatement pstmt = conn.prepareStatement(materialSql);
                     ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        stats.setTotalMaterials(rs.getInt(1));
                        System.out.println("📊 Total Materials: " + stats.getTotalMaterials());
                    }
                }
            } catch (SQLException e) {
                System.out.println("⚠️ Materials table not found or error: " + e.getMessage());
                stats.setTotalMaterials(0);
            }

            try {
                String lowStockSql = "SELECT COUNT(*) FROM materials WHERE quantity_in_stock <= low_stock_threshold";
                try (PreparedStatement pstmt = conn.prepareStatement(lowStockSql);
                     ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        stats.setLowStockItems(rs.getInt(1));
                        System.out.println("📊 Low Stock Items: " + stats.getLowStockItems());
                    }
                }
            } catch (SQLException e) {
                System.out.println("⚠️ Low stock query error: " + e.getMessage());
                stats.setLowStockItems(0);
            }

            try {
                String cleanerSql = "SELECT COUNT(*) FROM cleaners WHERE status = 'Active'";
                try (PreparedStatement pstmt = conn.prepareStatement(cleanerSql);
                     ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        stats.setTotalCleaners(rs.getInt(1));
                        System.out.println("📊 Total Cleaners: " + stats.getTotalCleaners());
                    }
                }
            } catch (SQLException e) {
                System.out.println("⚠️ Cleaners query error: " + e.getMessage());
                stats.setTotalCleaners(0);
            }

            try {
                String supplierSql = "SELECT COUNT(*) FROM suppliers WHERE status = 'Active'";
                try (PreparedStatement pstmt = conn.prepareStatement(supplierSql);
                     ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        stats.setTotalSuppliers(rs.getInt(1));
                        System.out.println("📊 Total Suppliers: " + stats.getTotalSuppliers());
                    }
                }
            } catch (SQLException e) {
                System.out.println("⚠️ Suppliers query error: " + e.getMessage());
                stats.setTotalSuppliers(0);
            }

            try {
                String issuanceSql = "SELECT COUNT(*) FROM issuance WHERE issue_date >= CURRENT_DATE - INTERVAL '30 days'";
                try (PreparedStatement pstmt = conn.prepareStatement(issuanceSql);
                     ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        stats.setRecentIssuances(rs.getInt(1));
                        System.out.println("📊 Recent Issuances: " + stats.getRecentIssuances());
                    }
                }
            } catch (SQLException e) {
                System.out.println("⚠️ Issuances query error: " + e.getMessage());
                stats.setRecentIssuances(0);
            }

            try {
                String userSql = "SELECT COUNT(*) FROM users WHERE is_active = true";
                try (PreparedStatement pstmt = conn.prepareStatement(userSql);
                     ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        stats.setTotalUsers(rs.getInt(1));
                        System.out.println("📊 Total Users: " + stats.getTotalUsers());
                    }
                }
            } catch (SQLException e) {
                System.out.println("⚠️ Users query error: " + e.getMessage());
                stats.setTotalUsers(0);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error getting dashboard stats: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("📊 Final Stats: " +
                "Materials=" + stats.getTotalMaterials() +
                ", LowStock=" + stats.getLowStockItems() +
                ", Cleaners=" + stats.getTotalCleaners() +
                ", Suppliers=" + stats.getTotalSuppliers() +
                ", Issuances=" + stats.getRecentIssuances() +
                ", Users=" + stats.getTotalUsers());

        return stats;
    }
}
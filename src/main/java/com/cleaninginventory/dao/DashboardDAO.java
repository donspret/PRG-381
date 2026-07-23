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

        try (Connection conn = DBConnection.getConnection()) {

            // Total Users
            stats.setTotalUsers(getCount(conn, "SELECT COUNT(*) FROM users WHERE is_active = true"));

            // Total Materials
            stats.setTotalMaterials(getCount(conn, "SELECT COUNT(*) FROM materials"));

            // Low Stock Items
            stats.setLowStockItems(getCount(conn, "SELECT COUNT(*) FROM materials WHERE quantity_in_stock <= low_stock_threshold"));

            // Total Cleaners
            stats.setTotalCleaners(getCount(conn, "SELECT COUNT(*) FROM cleaners"));

            // Recent Issuances (last 7 days)
            stats.setRecentIssuances(getCount(conn, "SELECT COUNT(*) FROM issuance WHERE issuance_date >= CURRENT_DATE - INTERVAL '7 days'"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stats;
    }

    private int getCount(Connection conn, String sql) {
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

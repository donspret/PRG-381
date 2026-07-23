package com.cleaninginventory.model;

public class DashboardStats {
    private int totalUsers;
    private int totalMaterials;
    private int lowStockItems;
    private int totalCleaners;
    private int recentIssuances;

    public DashboardStats() {
        this.totalUsers = 0;
        this.totalMaterials = 0;
        this.lowStockItems = 0;
        this.totalCleaners = 0;
        this.recentIssuances = 0;
    }

    public DashboardStats(int totalUsers, int totalMaterials, int lowStockItems,
                          int totalCleaners, int recentIssuances) {
        this.totalUsers = totalUsers;
        this.totalMaterials = totalMaterials;
        this.lowStockItems = lowStockItems;
        this.totalCleaners = totalCleaners;
        this.recentIssuances = recentIssuances;
    }

    // Getters and Setters
    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public int getTotalMaterials() {
        return totalMaterials;
    }

    public void setTotalMaterials(int totalMaterials) {
        this.totalMaterials = totalMaterials;
    }

    public int getLowStockItems() {
        return lowStockItems;
    }

    public void setLowStockItems(int lowStockItems) {
        this.lowStockItems = lowStockItems;
    }

    public int getTotalCleaners() {
        return totalCleaners;
    }

    public void setTotalCleaners(int totalCleaners) {
        this.totalCleaners = totalCleaners;
    }

    public int getRecentIssuances() {
        return recentIssuances;
    }

    public void setRecentIssuances(int recentIssuances) {
        this.recentIssuances = recentIssuances;
    }

    @Override
    public String toString() {
        return "DashboardStats{" +
                "totalUsers=" + totalUsers +
                ", totalMaterials=" + totalMaterials +
                ", lowStockItems=" + lowStockItems +
                ", totalCleaners=" + totalCleaners +
                ", recentIssuances=" + recentIssuances +
                '}';
    }
}
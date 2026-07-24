package com.cleaninginventory.model;

public class DashboardStats {
    private int totalMaterials;
    private int lowStockItems;
    private int totalCleaners;
    private int totalSuppliers;
    private int recentIssuances;
    private int totalUsers;

    public DashboardStats() {}

    public DashboardStats(int totalMaterials, int lowStockItems, int totalCleaners,
                          int totalSuppliers, int recentIssuances, int totalUsers) {
        this.totalMaterials = totalMaterials;
        this.lowStockItems = lowStockItems;
        this.totalCleaners = totalCleaners;
        this.totalSuppliers = totalSuppliers;
        this.recentIssuances = recentIssuances;
        this.totalUsers = totalUsers;
    }

    public int getTotalMaterials() { return totalMaterials; }
    public void setTotalMaterials(int totalMaterials) { this.totalMaterials = totalMaterials; }

    public int getLowStockItems() { return lowStockItems; }
    public void setLowStockItems(int lowStockItems) { this.lowStockItems = lowStockItems; }

    public int getTotalCleaners() { return totalCleaners; }
    public void setTotalCleaners(int totalCleaners) { this.totalCleaners = totalCleaners; }

    public int getTotalSuppliers() { return totalSuppliers; }
    public void setTotalSuppliers(int totalSuppliers) { this.totalSuppliers = totalSuppliers; }

    public int getRecentIssuances() { return recentIssuances; }
    public void setRecentIssuances(int recentIssuances) { this.recentIssuances = recentIssuances; }

    public int getTotalUsers() { return totalUsers; }
    public void setTotalUsers(int totalUsers) { this.totalUsers = totalUsers; }
}
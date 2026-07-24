package com.cleaninginventory.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Report {
    private int reportId;
    private String reportType;
    private String reportName;
    private String generatedBy;
    private Timestamp generatedAt;
    private String format;
    private String data;

    public static final String TYPE_INVENTORY = "Inventory Report";
    public static final String TYPE_LOW_STOCK = "Low Stock Report";
    public static final String TYPE_ISSUANCE_HISTORY = "Issuance History";
    public static final String TYPE_MATERIAL_USAGE = "Material Usage Report";

    public static class InventoryItem {
        private int materialId;
        private String materialName;
        private String category;
        private int quantityInStock;
        private int lowStockThreshold;
        private String unit;
        private String status;

        public int getMaterialId() { return materialId; }
        public void setMaterialId(int materialId) { this.materialId = materialId; }

        public String getMaterialName() { return materialName; }
        public void setMaterialName(String materialName) { this.materialName = materialName; }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public int getQuantityInStock() { return quantityInStock; }
        public void setQuantityInStock(int quantityInStock) { this.quantityInStock = quantityInStock; }

        public int getLowStockThreshold() { return lowStockThreshold; }
        public void setLowStockThreshold(int lowStockThreshold) { this.lowStockThreshold = lowStockThreshold; }

        public String getUnit() { return unit; }
        public void setUnit(String unit) { this.unit = unit; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class IssuanceItem {
        private int issuanceId;
        private String materialName;
        private String cleanerName;
        private int quantity;
        private Timestamp issueDate;
        private String status;

        public int getIssuanceId() { return issuanceId; }
        public void setIssuanceId(int issuanceId) { this.issuanceId = issuanceId; }

        public String getMaterialName() { return materialName; }
        public void setMaterialName(String materialName) { this.materialName = materialName; }

        public String getCleanerName() { return cleanerName; }
        public void setCleanerName(String cleanerName) { this.cleanerName = cleanerName; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public Timestamp getIssueDate() { return issueDate; }
        public void setIssueDate(Timestamp issueDate) { this.issueDate = issueDate; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class MaterialUsageItem {
        private String materialName;
        private int totalIssued;
        private int remainingStock;
        private String unit;

        public String getMaterialName() { return materialName; }
        public void setMaterialName(String materialName) { this.materialName = materialName; }

        public int getTotalIssued() { return totalIssued; }
        public void setTotalIssued(int totalIssued) { this.totalIssued = totalIssued; }

        public int getRemainingStock() { return remainingStock; }
        public void setRemainingStock(int remainingStock) { this.remainingStock = remainingStock; }

        public String getUnit() { return unit; }
        public void setUnit(String unit) { this.unit = unit; }
    }
}

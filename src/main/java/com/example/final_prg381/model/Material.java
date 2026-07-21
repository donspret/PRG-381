package com.example.final_prg381.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Material {
    private int materialId;
    private String materialName;
    private String category;
    private String unit;
    private int quantityInStock;
    private int lowStockThreshold;
    private BigDecimal unitPrice;
    private int supplierId;
    private Timestamp lastUpdated;

    public Material() {
    }

    public Material(int materialId, String materialName, String category, String unit,
                    int quantityInStock, int lowStockThreshold, BigDecimal unitPrice,
                    int supplierId) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.category = category;
        this.unit = unit;
        this.quantityInStock = quantityInStock;
        this.lowStockThreshold = lowStockThreshold;
        this.unitPrice = unitPrice;
        this.supplierId = supplierId;
    }

    public int getMaterialId() { return materialId; }
    public void setMaterialId(int materialId) { this.materialId = materialId; }

    public String getMaterialName() { return materialName; }
    public void setMaterialName(String materialName) { this.materialName = materialName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public int getQuantityInStock() { return quantityInStock; }
    public void setQuantityInStock(int quantityInStock) { this.quantityInStock = quantityInStock; }

    public int getLowStockThreshold() { return lowStockThreshold; }
    public void setLowStockThreshold(int lowStockThreshold) { this.lowStockThreshold = lowStockThreshold; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    public Timestamp getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Timestamp lastUpdated) { this.lastUpdated = lastUpdated; }

    public boolean isLowStock() {
        return quantityInStock <= lowStockThreshold;
    }
}
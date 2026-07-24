package com.cleaninginventory.model;

import java.sql.Timestamp;

public class StockIssuance {

    private int issuanceId;
    private int materialId;
    private String materialName;   // convenience field for display in reports
    private int cleanerId;
    private String cleanerName;    // convenience field for display in reports
    private int quantityIssued;
    private Timestamp issuedDate;
    private String issuedBy;

    public StockIssuance() {
    }

    public int getIssuanceId() {
        return issuanceId;
    }

    public void setIssuanceId(int issuanceId) {
        this.issuanceId = issuanceId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public int getCleanerId() {
        return cleanerId;
    }

    public void setCleanerId(int cleanerId) {
        this.cleanerId = cleanerId;
    }

    public String getCleanerName() {
        return cleanerName;
    }

    public void setCleanerName(String cleanerName) {
        this.cleanerName = cleanerName;
    }

    public int getQuantityIssued() {
        return quantityIssued;
    }

    public void setQuantityIssued(int quantityIssued) {
        this.quantityIssued = quantityIssued;
    }

    public Timestamp getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Timestamp issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }
}

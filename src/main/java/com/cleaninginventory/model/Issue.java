package com.cleaninginventory.model;

import java.sql.Timestamp;

public class Issue {
    private int issueId;
    private String issueType;
    private String description;
    private String severity;
    private int materialId;
    private String materialName;
    private int supplierId;
    private String supplierName;
    private int storekeeperId;
    private String storekeeperName;
    private String status;
    private String resolution;
    private int resolvedBy;
    private String resolvedByName;
    private Timestamp createdAt;
    private Timestamp resolvedAt;

    // Constants for issue types
    public static final String TYPE_DEFECTIVE = "Defective/Low-Quality Products";
    public static final String TYPE_TECHNICAL = "Technical Failures";
    public static final String TYPE_DELAYS = "Delays";
    public static final String TYPE_SUPPLIER = "Problems with Suppliers";
    public static final String TYPE_SHORTAGE = "Material Shortages";
    public static final String TYPE_CONFLICT = "Conflicts with Staff";

    // Constants for severity levels
    public static final String SEVERITY_LOW = "Low";
    public static final String SEVERITY_MEDIUM = "Medium";
    public static final String SEVERITY_HIGH = "High";
    public static final String SEVERITY_CRITICAL = "Critical";

    // Constants for status
    public static final String STATUS_OPEN = "Open";
    public static final String STATUS_IN_PROGRESS = "In Progress";
    public static final String STATUS_RESOLVED = "Resolved";
    public static final String STATUS_CLOSED = "Closed";

    // Constructors
    public Issue() {}

    public Issue(int issueId, String issueType, String description, String severity,
                 int materialId, int supplierId, int storekeeperId, String status) {
        this.issueId = issueId;
        this.issueType = issueType;
        this.description = description;
        this.severity = severity;
        this.materialId = materialId;
        this.supplierId = supplierId;
        this.storekeeperId = storekeeperId;
        this.status = status;
    }

    // Getters and Setters
    public int getIssueId() { return issueId; }
    public void setIssueId(int issueId) { this.issueId = issueId; }

    public String getIssueType() { return issueType; }
    public void setIssueType(String issueType) { this.issueType = issueType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public int getMaterialId() { return materialId; }
    public void setMaterialId(int materialId) { this.materialId = materialId; }

    public String getMaterialName() { return materialName; }
    public void setMaterialName(String materialName) { this.materialName = materialName; }

    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public int getStorekeeperId() { return storekeeperId; }
    public void setStorekeeperId(int storekeeperId) { this.storekeeperId = storekeeperId; }

    public String getStorekeeperName() { return storekeeperName; }
    public void setStorekeeperName(String storekeeperName) { this.storekeeperName = storekeeperName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }

    public int getResolvedBy() { return resolvedBy; }
    public void setResolvedBy(int resolvedBy) { this.resolvedBy = resolvedBy; }

    public String getResolvedByName() { return resolvedByName; }
    public void setResolvedByName(String resolvedByName) { this.resolvedByName = resolvedByName; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(Timestamp resolvedAt) { this.resolvedAt = resolvedAt; }

    public String getSeverityColor() {
        switch (severity) {
            case SEVERITY_LOW: return "green";
            case SEVERITY_MEDIUM: return "orange";
            case SEVERITY_HIGH: return "red";
            case SEVERITY_CRITICAL: return "darkred";
            default: return "gray";
        }
    }

    public String getStatusColor() {
        switch (status) {
            case STATUS_OPEN: return "red";
            case STATUS_IN_PROGRESS: return "orange";
            case STATUS_RESOLVED: return "green";
            case STATUS_CLOSED: return "gray";
            default: return "gray";
        }
    }
}

package com.example.final_prg381.model;

public class Material{

    private int materialId;
    private String name;
    private int quantity;
    private int reorderLevel;
    private String unit;

    public Material(){
    }
    public Material(int materialId, String name,int quantity, int reorderLevel,String unit){
        this.materialId = materialId;
        this.name= name;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.unit = unit;
    }

    public int getMaterialId(){
        return materialId;
    }

    public void setMaterialId(int materialId){
        this.materialId = materialId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isLowStock() {
        return quantity <= reorderLevel;
    }

}
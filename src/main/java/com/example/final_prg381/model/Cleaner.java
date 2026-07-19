package com.example.final_prg381.model;

public class Cleaner {

    private int cleanerId;
    private String firstName;
    private String lastName;
    private String department;

    public Cleaner() {
    }

    public Cleaner(int cleanerId, String firstName, String lastName, String department) {
        this.cleanerId = cleanerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }

    public int getCleanerId() {
        return cleanerId;
    }

    public void setCleanerId(int cleanerId) {
        this.cleanerId = cleanerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}

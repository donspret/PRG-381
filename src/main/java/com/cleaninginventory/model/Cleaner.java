package com.cleaninginventory.model;

public class Cleaner {
    private int cleanerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String department;
    private String status; // Active, Inactive, On Leave
    private String shift; // Morning, Afternoon, Night
    private String notes;

    public Cleaner() {}

    public Cleaner(int cleanerId, String firstName, String lastName, String email,
                   String phone, String address, String department, String status,
                   String shift, String notes) {
        this.cleanerId = cleanerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.department = department;
        this.status = status;
        this.shift = shift;
        this.notes = notes;
    }

    public int getCleanerId() { return cleanerId; }
    public void setCleanerId(int cleanerId) { this.cleanerId = cleanerId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() { return firstName + " " + lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
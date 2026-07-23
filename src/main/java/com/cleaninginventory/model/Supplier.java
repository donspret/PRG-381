package com.cleaninginventory.model;

public class Supplier {
    private int supplierId;
    private String companyName;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String postalCode;
    private String country;
    private String status; // Active, Inactive
    private String notes;

    public Supplier() {}

    public Supplier(int supplierId, String companyName, String contactPerson,
                    String email, String phone, String address, String city,
                    String postalCode, String country, String status, String notes) {
        this.supplierId = supplierId;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.status = status;
        this.notes = notes;
    }

    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}

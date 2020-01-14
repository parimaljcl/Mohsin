package com.example.mohsin;

public class Teacher {
    private String id;
    private String name;
    private String department;
    private String designation;
    private String email;
    private String phone;
    private String imageUrl;

    public Teacher() {

    }

    public Teacher(String id, String name, String department, String designation, String email, String phone,String imageUrl) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.designation = designation;
        this.email = email;
        this.phone = phone;
        this.imageUrl=imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}


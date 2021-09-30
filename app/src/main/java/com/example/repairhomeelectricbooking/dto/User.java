package com.example.repairhomeelectricbooking.dto;

public class User {
    public String email,password,fullName,phone,adress;

    public User() { }

    public User(String email, String password, String fullName, String phone, String adress) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}

package com.example.repairhomeelectricbooking.dto;

public class User {
    public String userID,email,password,fullName,phone,adress,image;
    public LocationApp location;

    public User() { }

    public User(String userID, String email, String password, String fullName, String phone, String adress, String image, LocationApp location) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.adress = adress;
        this.image = image;
        this.location = location;
    }

    public User(String userID, String fullName, String adress){
        this.userID=userID;
        this.fullName=fullName;
        this.adress=adress;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public LocationApp getLocation() {
        return location;
    }

    public void setLocation(LocationApp location) {
        this.location = location;
    }
}

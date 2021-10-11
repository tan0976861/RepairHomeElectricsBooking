package com.example.repairhomeelectricbooking.dto;

import java.io.Serializable;

public class Worker implements Serializable, Comparable<Worker> {
    public String workerID,email, password, fullName, phone, address, type, image;
    public boolean status, active;
     public double fee,distance,ratingPoint;
     public LocationApp location;




    public Worker() {
    }

    public Worker(String workerID,String email, String password, String fullName, String phone, String address, String type, String image, boolean status, boolean active, double fee, LocationApp location) {
        this.workerID=workerID;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.type = type;
        this.image = image;
        this.status = status;
        this.active = active;
        this.fee = fee;
        this.location=location;

    }

    public String getWorkerID() {
        return workerID;
    }

    public void setWorkerID(String workerID) {
        this.workerID = workerID;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public LocationApp getLocation() {
        return location;
    }

    public void setLocation(LocationApp location) {
        this.location = location;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getRatingPoint() {
        return ratingPoint;
    }

    public void setRatingPoint(double ratingPoint) {
        this.ratingPoint = ratingPoint;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "workerId='" + workerID + '\'' +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                ", status=" + status +
                ", active=" + active +
                ", fee=" + fee +
                ", location=" + location +
                ", Distance=" + distance +
                ", Rating=" + ratingPoint +
                '}';
    }

    @Override
    public int compareTo(Worker worker) {
        if (this.distance < worker.distance) {
            return -1;
        } else {
            if (this.distance == worker.distance) {
                return 0;

            } else {
                return 1;
            }
        }
    }
}

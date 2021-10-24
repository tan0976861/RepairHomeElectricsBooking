package com.example.repairhomeelectricbooking.dto;

public class Rating {
    public String customerId,workerId,comment,date;
    double ratingPoint;

    public Rating() {
    }

    public Rating(String customerId, String workerId, String comment, String date, double ratingPoint) {
        this.customerId = customerId;
        this.workerId = workerId;
        this.comment = comment;
        this.date = date;
        this.ratingPoint = ratingPoint;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getRatingPoint() {
        return ratingPoint;
    }

    public void setRatingPoint(double ratingPoint) {
        this.ratingPoint = ratingPoint;
    }
}

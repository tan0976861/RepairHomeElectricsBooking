package com.example.repairhomeelectricbooking.dto;

public class Rating {
    public String customerId,workerId,comment;
    double ratingPoint;

    public Rating() {
    }

    public Rating(String customerId, String workerId, String comment, double ratingPoint) {
        this.customerId = customerId;
        this.workerId = workerId;
        this.comment = comment;
        this.ratingPoint = ratingPoint;
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

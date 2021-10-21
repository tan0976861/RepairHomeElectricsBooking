package com.example.repairhomeelectricbooking.dto;

import java.util.Date;

public class Order {
    Worker worker;
    User user;
    public long orderID;
    public String problem;
    public Double fee;
    public String createDate;
    public int status;

    public Order(Worker worker, User user, long orderID, String problem, Double fee, String createDate, int status) {
        this.worker = worker;
        this.user = user;
        this.orderID = orderID;
        this.problem = problem;
        this.fee = fee;
        this.createDate = createDate;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order() {
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}

package com.example.repairhomeelectricbooking.dto;

import java.util.Date;

public class Order {
    Worker worker;
    User user;
    public String problem;
    public Double fee;
    public String createDate;

    public Order(Worker worker, User user, String problem, Double fee, String createDate) {
        this.worker = worker;
        this.user = user;
        this.problem = problem;
        this.fee = fee;
        this.createDate = createDate;
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

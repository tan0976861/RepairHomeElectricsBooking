package com.example.repairhomeelectricbooking.dto;

import java.util.Date;
import java.util.List;

public class Order {
    Worker worker;
    User user;
    List<Service> listService;
    public long orderID;
    public String problem;
    public Double fee;
    public String createDate;
    public int status;

    public Order(Worker worker, User user, List<Service> listService, long orderID, String problem, Double fee, String createDate, int status) {
        this.worker = worker;
        this.user = user;
        this.listService = listService;
        this.orderID = orderID;
        this.problem = problem;
        this.fee = fee;
        this.createDate = createDate;
        this.status = status;
    }

    public List<Service> getListService() {
        return listService;
    }

    public void setListService(List<Service> listService) {
        this.listService = listService;
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

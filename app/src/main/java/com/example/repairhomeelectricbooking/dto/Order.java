package com.example.repairhomeelectricbooking.dto;

import java.util.Date;

public class Order {
    public String costomerId,workerId, problem;
    public Double fee;
    public Date createDate;

    public Order() {
    }

    public Order(String costomerId, String workerId, String problem, Double fee, Date createDate) {
        this.costomerId = costomerId;
        this.workerId = workerId;
        this.problem = problem;
        this.fee = fee;
        this.createDate = createDate;
    }

    public String getCostomerId() {
        return costomerId;
    }

    public void setCostomerId(String costomerId) {
        this.costomerId = costomerId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

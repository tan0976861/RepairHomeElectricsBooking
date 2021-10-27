package com.example.repairhomeelectricbooking.dto;

public class Service {
    public String serviceName;
    public double fee;

    public Service(){}

    public Service(String serviceName, double fee) {
        this.serviceName = serviceName;
        this.fee = fee;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}

package com.fireboy.booka.model;

public class Service {
    private String name;
    private double price;
    private int duration;
    private String businessId;

    public Service() {
    }

    public Service(String name, double price, int duration, String businessId) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.businessId = businessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}

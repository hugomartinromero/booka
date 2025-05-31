package com.fireboy.booka.model;

public class Reservation {
    private String userId;
    private String businessId;
    private String service;
    private String date; // formato "2025-06-01"
    private String time; // formato "15:00"

    public Reservation() {
    }

    public Reservation(String userId, String businessId, String service, String date, String time) {
        this.userId = userId;
        this.businessId = businessId;
        this.service = service;
        this.date = date;
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getService() {
        return service;
    }

    public void setService(String serviceId) {
        this.service = serviceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
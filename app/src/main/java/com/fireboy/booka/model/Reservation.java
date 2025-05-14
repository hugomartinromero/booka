package com.fireboy.booka.model;

public class Reservation {
    private String id;
    private String userId;
    private String businessId;
    private String serviceId;
    private String date; // formato "2025-06-01"
    private String time; // formato "15:00"
    private String status;

    public Reservation() {
    }

    public Reservation(String id, String userId, String businessId, String serviceId, String date, String time, String status) {
        this.id = id;
        this.userId = userId;
        this.businessId = businessId;
        this.serviceId = serviceId;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
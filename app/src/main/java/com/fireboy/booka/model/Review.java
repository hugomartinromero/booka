package com.fireboy.booka.model;

import com.google.firebase.Timestamp;

public class Review {
    private String userId;
    private String businessId;
    private double rating;
    private String comment;
    private Timestamp timestamp;

    public Review() {}

    public Review(String userId, String businessId, double rating, String comment, Timestamp timestamp) {
        this.userId = userId;
        this.businessId = businessId;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

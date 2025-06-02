package com.fireboy.booka.model;

import java.util.List;
import java.util.Map;

public class Business {
    private String id;
    private String name;
    private String category;
    private String address;
    private String img;
    private String owner;
    private Map<String, DaySchedule> schedule;
    private float rating;
    private List<Service> services;

    public Business() {
    }

    public Business(String id, String name, String category, String address, String img, String owner,
                    Map<String, DaySchedule> schedule, float rating, List<Service> services) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.address = address;
        this.img = img;
        this.owner = owner;
        this.schedule = schedule;
        this.rating = rating;
        this.services = services;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Map<String, DaySchedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, DaySchedule> schedule) {
        this.schedule = schedule;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}

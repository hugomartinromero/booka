package com.fireboy.booka.model;

import java.util.List;
import java.util.Map;

public class Business {
    private String id;
    private String name;
    private Category category;
    private String address;
    private User owner;
    private Map<String, DaySchedule> schedule;
    private float rating;
    private List<Service> services;

    public Business() {
    }

    public Business(String id, String name, Category category, String address, User owner, Map<String, DaySchedule> schedule, float rating, List<Service> services) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.address = address;
        this.owner = owner;
        this.schedule = schedule;
        this.rating = rating;
        this.services = services;
    }

    public String getId() {
        return name;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
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

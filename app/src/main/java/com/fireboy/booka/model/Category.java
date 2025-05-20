package com.fireboy.booka.model;

public class Category {
    private String name;
    private boolean active;

    public Category() {
    }

    public Category(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

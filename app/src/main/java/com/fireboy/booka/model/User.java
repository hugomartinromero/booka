package com.fireboy.booka.model;

public class User {
    private String id;
    private String email;
    private String username;
    private String role;
    private String img;

    public User() {
    }

    public User(String id, String email, String username, String role, String img) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

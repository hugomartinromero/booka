package com.fireboy.booka.model;

public class User {
    private String email;
    private String username;
    private String role;
    private String photo;

    public User() {
    }

    public User(String email, String username, String role, String img) {
        this.email = email;
        this.username = username;
        this.role = role;
        this.photo = img;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

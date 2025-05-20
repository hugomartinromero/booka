package com.fireboy.booka.model;

public class User {
    private String id;
    private String email;
    private String username;
    private String rol;
    private String img;

    public User() {
    }

    public User(String id, String email, String username, String rol, String img) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.rol = rol;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

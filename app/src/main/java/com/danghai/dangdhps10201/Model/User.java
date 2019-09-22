package com.danghai.dangdhps10201.Model;

public class User {
    private String id;
    private String fullname;

    public User(String id, String fullname) {
        this.id = id;
        this.fullname = fullname;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}

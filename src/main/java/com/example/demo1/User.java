package com.example.demo1;

public class User {

    public String name;
    public String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public User() {
        this.name = "";
        this.email = "";
    }
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}


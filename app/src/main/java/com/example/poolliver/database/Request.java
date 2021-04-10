package com.example.poolliver.database;

public class Request {
    String username;
    String price;

    public Request(String username, String price) {
        this.username = username;
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

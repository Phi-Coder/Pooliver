package com.example.poolliver.database;

public class Request {
    String username;
    String price;
    String phoneNum;
    String uid;

    public Request(String username, String price, String phoneNum) {
        this.username = username;
        this.price = price;
        this.phoneNum = phoneNum;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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

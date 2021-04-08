package com.example.poolliver.database;

import java.util.List;

public class dataHolder {

    String name;
    String uid;
    String itemtype, pickupAddress, dropAddress;
    String time;
    int price, phoneNum;
    double pLat, pLong, dLong, dLat;

    public dataHolder(String name, String pickupAddress, String dropAddress, String itemtype, int price, double pLat, double pLong, double dLong, double dLat) {
        this.name = name;
        this.pickupAddress = pickupAddress;
        this.dropAddress = dropAddress;
        this.itemtype = itemtype;
        this.price = price;
        this.pLat = pLat;
        this.pLong = pLong;
        this.dLong = dLong;
        this.dLat = dLat;
    }

    public dataHolder() {

    }

    public dataHolder(String name, String pickupAddress, String dropAddress, int price, String itemtype, String time) {
        this.name = name;
        this.time = time;
        this.pickupAddress = pickupAddress;
        this.dropAddress = dropAddress;
        this.itemtype = itemtype;
        this.price = price;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getdropAddress() {
        return dropAddress;
    }

    public void setdropAddress(String dropAddress) {
        this.dropAddress = dropAddress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getitemtype() {
        return itemtype;
    }

    public void setitemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getpLat() {
        return pLat;
    }

    public void setpLat(double pLat) {
        this.pLat = pLat;
    }

    public double getpLong() {
        return pLong;
    }

    public void setpLong(double pLong) {
        this.pLong = pLong;
    }

    public double getdLong() {
        return dLong;
    }

    public void setdLong(double dLong) {
        this.dLong = dLong;
    }

    public double getdLat() {
        return dLat;
    }

    public void setdLat(double dLat) {
        this.dLat = dLat;
    }
}

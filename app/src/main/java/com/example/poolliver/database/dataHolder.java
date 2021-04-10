package com.example.poolliver.database;

import java.util.List;

public class dataHolder {

    String name;
    String uid;
    String itemtype, pickupAddress, dropAddress;
    String time;

    int price, phoneNum;
    String pickupLat, pickupLong, dropLat, dropLong;

    public dataHolder(String name, String pickupAddress, String dropAddress, String itemtype, int price) {
        this.name = name;
        this.pickupAddress = pickupAddress;
        this.dropAddress = dropAddress;
        this.itemtype = itemtype;
        this.price = price;
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

    public String getPickupLat() {
        return pickupLat;
    }

    public void setPickupLat(String pickupLat) {
        this.pickupLat = pickupLat;
    }

    public String getPickupLong() {
        return pickupLong;
    }

    public void setPickupLong(String pickupLong) {
        this.pickupLong = pickupLong;
    }

    public String getDropLat() {
        return dropLat;
    }

    public void setDropLat(String dropLat) {
        this.dropLat = dropLat;
    }

    public String getDropLong() {
        return dropLong;
    }

    public void setDropLong(String dropLong) {
        this.dropLong = dropLong;
    }
}

package com.example.zegerd.nina_del_burrito.classes;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

public class Order implements Serializable{
    private int quantity;
    private String description;
    private GregorianCalendar orderDate;
    private String itemId;
    private String itemName;
    private String clientName;
    private String key;
    private String clientId;
    private String vendorId;
    private double lat, lng;
    private int hour, minute;

    public Order () {
    }

    public Order (int q, String desc, GregorianCalendar date, String id, String name, String k, String clientName, String cId, LatLng location, String vId) {
        this.quantity = q;
        this.description = desc;
//        this.orderDate = date;
        this.hour = date.get(GregorianCalendar.HOUR);
        this.minute = date.get(GregorianCalendar.MINUTE);
        this.itemId = id;
        this.itemName = name;
        this.key = k;
        this.clientName = clientName;
        this.clientId = cId;
        this.lat = location.latitude;
        this.lng = location.longitude;
        this.vendorId = vId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    //    public LatLng getLocation() {
//        return location;
//    }
//
//    public void setLocation(LatLng location) {
//        this.location = location;
//    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public GregorianCalendar getOrderDate() {
//        return orderDate;
//    }
//
//    public void setOrderDate(GregorianCalendar orderDate) {
//        this.orderDate = orderDate;
//    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}

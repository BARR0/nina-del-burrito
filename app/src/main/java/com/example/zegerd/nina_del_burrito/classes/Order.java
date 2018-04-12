package com.example.zegerd.nina_del_burrito.classes;

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

    public Order () {
    }

    public Order (int q, String desc, GregorianCalendar date, String id, String name, String k, String clientName, String cId) {
        this.quantity = q;
        this.description = desc;
        this.orderDate = date;
        this.itemId = id;
        this.itemName = name;
        this.key = k;
        this.clientName = clientName;
        this.clientId = cId;
    }

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

    public GregorianCalendar getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(GregorianCalendar orderDate) {
        this.orderDate = orderDate;
    }

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

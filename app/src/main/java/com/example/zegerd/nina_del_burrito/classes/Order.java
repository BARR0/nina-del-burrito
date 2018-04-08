package com.example.zegerd.nina_del_burrito.classes;

import java.util.Date;

public class Order {
    private int quantity;
    private String description;
    private Date orderDate;
    private String itemId;
    private String itemName;

    public Order () {
    }

    public Order (int q, String desc, Date date, String id, String name) {
        this.quantity = q;
        this.description = desc;
        this.orderDate = date;
        this.itemId = id;
        this.itemName = name;
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
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
}

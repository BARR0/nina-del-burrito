package com.example.zegerd.nina_del_burrito.classes;

import android.text.BoringLayout;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zegerd on 2/20/2018.
 */

public class Item implements Serializable{
    private String nombre;
    private String descripcion;
    private float precio;
    private int cantidad;
    private boolean disponible;
    private String vendorid;
    private Map<String, Boolean> categories;
    private String itemPicture;
    private double rating;
    private int rateQuantity;

    public Item() {

    }

    public Map<String, Boolean> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, Boolean> categories) {
        this.categories = categories;
    }

    public Item (String nom, String desc, float pre, String vendorid, Map<String, Boolean> categories, int cantidad, double rating, int rateQuantity) {
        this.nombre = nom;
        this.descripcion = desc;
        this.precio = pre;
        this.cantidad = cantidad;
        this.disponible = cantidad > 0;
        this.vendorid = vendorid;
        this.categories = categories;
        this.itemPicture = null;
        this.rating = rating;
        this.rateQuantity = rateQuantity;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return cantidad > 0;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String toString() {
        return nombre + ", $" + precio;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
    }

    public String getItemPicture() {
        return itemPicture;
    }

    public void setItemPicture(String itemPicture) {
        this.itemPicture = itemPicture;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRateQuantity() {
        return rateQuantity;
    }

    public void setRateQuantity(int rateQuantity) {
        this.rateQuantity = rateQuantity;
    }
}

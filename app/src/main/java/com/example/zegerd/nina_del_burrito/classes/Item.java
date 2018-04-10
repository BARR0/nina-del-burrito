package com.example.zegerd.nina_del_burrito.classes;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zegerd on 2/20/2018.
 */

public class Item implements Serializable{
    private String nombre;
    private String descripcion;
    private float precio;
    private boolean disponible;
    private String vendorid;
    private List<String> categories;
    private String itemPicture;

    public Item() {

    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Item (String nom, String desc, float pre, String vendorid, List<String> categories) {
        this.nombre = nom;
        this.descripcion = desc;
        this.precio = pre;
        this.disponible = true;
        this.vendorid = vendorid;
        this.categories = categories;
        this.itemPicture = "";
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
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
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
}

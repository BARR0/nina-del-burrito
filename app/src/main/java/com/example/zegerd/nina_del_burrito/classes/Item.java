package com.example.zegerd.nina_del_burrito.classes;

/**
 * Created by Zegerd on 2/20/2018.
 */

public class Item {
    private String nombre;
    private String descripcion;
    private float precio;
    private boolean disponible;
    private String vendorid;

    public Item() {

    }

    public Item (String nom, String desc, float pre, String vendorid) {
        this.nombre = nom;
        this.descripcion = desc;
        this.precio = pre;
        this.disponible = true;
        this.vendorid = vendorid;
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
}

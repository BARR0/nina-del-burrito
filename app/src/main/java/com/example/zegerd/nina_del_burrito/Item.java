package com.example.zegerd.nina_del_burrito;

/**
 * Created by Zegerd on 2/20/2018.
 */

public class Item {
    private String nombre;
    private String descripcion;
    private float precio;
    private boolean disponible;

    public Item() {

    }

    public Item (String nom, String desc, float pre) {
        this.nombre = nom;
        this.descripcion = desc;
        this.precio = pre;
        this.disponible = true;
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
        return nombre + ", " + descripcion + ", $" + precio;
    }
}

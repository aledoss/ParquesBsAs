package com.example.ndiaz.parquesbsas.model;

import java.io.Serializable;

/**
 * Created by Lenwe on 13/10/2016.
 */

public class Parque implements Serializable {
    private int id;
    private int id_parque;
    private String nombre;
    private String descripcionCorta;
    private String descripcion;
    private String direccion;
    private String imagen;
    private String comuna;
    private String barrio;
    private String latitud;
    private String longitud;
    private int likes;
    private int hates;
    private String patioJuegos;

    public Parque() {
    }

    public int getId_parque() {
        return id_parque;
    }

    public void setId_parque(int id_parque) {
        this.id_parque = id_parque;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getHates() {
        return hates;
    }

    public void setHates(int hates) {
        this.hates = hates;
    }

    public String getPatioJuegos() {
        return patioJuegos;
    }

    public void setPatioJuegos(String patioJuegos) {
        this.patioJuegos = patioJuegos;
    }
}

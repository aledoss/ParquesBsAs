package com.example.ndiaz.parquesbsas.database;

import java.io.Serializable;

/**
 * Created by Lenwe on 13/10/2016.
 */

public class Parque implements Serializable {
    int id;
    int id_parque;
    String nombre;
    String descripcionCorta;
    String descripcion;
    String imagen;
    String latitud;
    String longitud;
    String barrio;


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
}

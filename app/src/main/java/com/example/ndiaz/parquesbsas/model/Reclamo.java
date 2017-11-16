package com.example.ndiaz.parquesbsas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Lenwe on 28/11/2016.
 */

public class Reclamo implements Serializable {

    private int id;
    private String parque;
    @JsonProperty("descripcion")
    private String nombre;
    @JsonProperty("comentarios")
    private String comentarios;
    @JsonProperty("fecha_creacion")
    private String fechaCreacion;
    @JsonProperty("latitud")
    private String latitud;
    @JsonProperty("longitud")
    private String longitud;
    @JsonProperty("imagen")
    private String imagen;
    @JsonProperty("cantidad")
    private Integer cantidad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParque() {
        return parque;
    }

    public void setParque(String parque) {
        this.parque = parque;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}

package com.example.ndiaz.parquesbsas.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;

/**
 * Created by Lenwe on 13/10/2016.
 */

public class Parque implements Serializable {
    @JsonProperty("id_parque")
    private Integer id_parque;
    @JsonProperty("comuna")
    private String comuna;
    @JsonProperty("barrio")
    private String barrio;
    @JsonProperty("nombre")
    private String nombre;
    private String descripcionCorta;
    @JsonProperty("descripcion")
    private String descripcion;
    @JsonProperty("direccion")
    private String direccion;
    @JsonProperty("imagen")
    private String imagen;
    @JsonProperty("latitud")
    private String latitud;
    @JsonProperty("longitud")
    private String longitud;
    @JsonProperty("likes")
    private Integer likes;
    @JsonProperty("hates")
    private Integer hates;
    @JsonProperty("patio_juegos")
    private Boolean hasPatioJuegos;
    @JsonProperty("wifi")
    private Boolean hasWifi;

    public Parque() {
    }

    public Integer getId_parque() {
        return id_parque;
    }

    public void setId_parque(Integer id_parque) {
        this.id_parque = id_parque;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionCorta() {
        return "Ubicado en el barrio de " + getBarrio();
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

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getHates() {
        return hates;
    }

    public void setHates(Integer hates) {
        this.hates = hates;
    }

    public Boolean getHasPatioJuegos() {
        return hasPatioJuegos;
    }

    public void setHasPatioJuegos(Boolean hasPatioJuegos) {
        this.hasPatioJuegos = hasPatioJuegos;
    }

    @JsonSetter("patio_juegos")
    public void setJsonHasPatioJuegos(String hasPatioJuegos) {
        this.hasPatioJuegos = hasPatioJuegos.equals("1");
    }

    @JsonGetter("patio_juegos")
    public String getJsonHasPatioJuegos() {
        return (hasPatioJuegos) ? "1" : "0";
    }

    @JsonSetter("wifi")
    public void setJsonHasWifi(String wifi) {
        hasWifi = wifi.equals("1");
    }

    @JsonGetter("wifi")
    public String getJsonHasWifi() {
        return (hasWifi) ? "1" : "0";
    }

    public Boolean getHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(Boolean hasWifi) {
        this.hasWifi = hasWifi;
    }
}

package com.example.ndiaz.parquesbsas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Calificacion {


    @JsonProperty("id_calificacion")
    private int idCalificacion;
    @JsonProperty("descripcion")
    private String nombre;
    private float porcBueno;
    private float porcRegular;
    private float porcMalo;

    public Calificacion() {
    }

    public Calificacion(int idCalificacion, String nombre) {
        this.idCalificacion = idCalificacion;
        this.nombre = nombre;
    }

    public int getIdCalificacion() {
        return idCalificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPorcBueno() {
        return porcBueno;
    }

    public float getPorcRegular() {
        return porcRegular;
    }

    public float getPorcMalo() {
        return porcMalo;
    }
}

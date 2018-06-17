package com.example.ndiaz.parquesbsas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Encuesta {

    @JsonProperty("id_encuesta")
    private int idEncuesta;
    @JsonProperty("descripcion")
    private String nombre;
    @JsonProperty("cantidad")
    private int cant;

    public Encuesta() {
    }

    public Encuesta(int idEncuesta, String nombre) {
        this.idEncuesta = idEncuesta;
        this.nombre = nombre;
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCant() {
        return cant;
    }
}

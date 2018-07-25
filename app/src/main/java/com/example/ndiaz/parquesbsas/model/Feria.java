package com.example.ndiaz.parquesbsas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Feria {

    @JsonProperty("id_feria_comun")
    private int idFeria;
    private String fecha;
    private String tipo;
    private String latitud;
    private String longitud;

    public Feria() {
    }

    public int getIdFeria() {
        return idFeria;
    }

    public void setIdFeria(int idFeria) {
        this.idFeria = idFeria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
}

package com.example.ndiaz.parquesbsas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PuntoVerde {

    private String tipo;
    private String materiales;
    @JsonProperty("dias_horarios")
    private String diasHorarios;
    private String latitud;
    private String longitud;

    public PuntoVerde() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMateriales() {
        return materiales;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }

    public String getDiasHorarios() {
        return diasHorarios;
    }

    public void setDiasHorarios(String diasHorarios) {
        this.diasHorarios = diasHorarios;
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

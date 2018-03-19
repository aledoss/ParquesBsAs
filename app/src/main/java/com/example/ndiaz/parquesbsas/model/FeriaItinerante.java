package com.example.ndiaz.parquesbsas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeriaItinerante {

    @JsonProperty("dias")
    private String fecha;
    private String direccion;
    private String latitud;
    private String longitud;

    public FeriaItinerante() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

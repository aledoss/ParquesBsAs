package com.example.ndiaz.parquesbsas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Actividad {

    @JsonProperty("id_actividad")
    private int id;
    private String nombre;
    @JsonProperty("desde")
    private String horaDesde;
    @JsonProperty("hasta")
    private String horaHasta;
    private String dia;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHoraDesde() {
        return horaDesde;
    }

    public String getHoraHasta() {
        return horaHasta;
    }

    public String getDia() {
        return dia;
    }
}

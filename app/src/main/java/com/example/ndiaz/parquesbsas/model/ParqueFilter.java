package com.example.ndiaz.parquesbsas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ParqueFilter {

    @JsonProperty("actividades")
    private List<Actividad> actividades;
    @JsonProperty("ferias")
    private List<Feria> ferias;
    @JsonProperty("feriaItineranteSelected")
    private boolean feriaItineranteSelected;
    @JsonProperty("centroSaludSelected")
    private boolean centroSaludSelected;
    @JsonProperty("patioJuegosSelected")
    private boolean patioJuegosSelected;

    public ParqueFilter(List<Actividad> actividades, List<Feria> ferias, boolean feriaItineranteSelected, boolean centroSaludSelected, boolean patioJuegosSelected) {
        this.actividades = actividades;
        this.ferias = ferias;
        this.feriaItineranteSelected = feriaItineranteSelected;
        this.centroSaludSelected = centroSaludSelected;
        this.patioJuegosSelected = patioJuegosSelected;
    }
}

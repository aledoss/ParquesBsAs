package com.example.ndiaz.parquesbsas.model;

import java.util.List;

public class ParqueFilter {

    private List<Actividad> actividades;
    private List<Feria> ferias;
    private boolean feriaItineranteSelected;
    private boolean centroSaludSelected;
    private boolean patioJuegosSelected;

    public ParqueFilter(List<Actividad> actividades, List<Feria> ferias, boolean feriaItineranteSelected, boolean centroSaludSelected, boolean patioJuegosSelected) {
        this.actividades = actividades;
        this.ferias = ferias;
        this.feriaItineranteSelected = feriaItineranteSelected;
        this.centroSaludSelected = centroSaludSelected;
        this.patioJuegosSelected = patioJuegosSelected;
    }
}

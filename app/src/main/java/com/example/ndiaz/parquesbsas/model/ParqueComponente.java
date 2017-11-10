package com.example.ndiaz.parquesbsas.model;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ParqueComponente {

    @JsonProperty("componente")
    private String nombreComponente;
    private int idParque;

    public ParqueComponente() {
    }

    public ParqueComponente(String nombreComponente) {
        this.nombreComponente = nombreComponente;
    }

    public String getNombreComponente() {
        return nombreComponente;
    }

    public void setNombreComponente(String nombreComponente) {
        this.nombreComponente = nombreComponente;
    }

    public int getIdParque() {
        return idParque;
    }

    public void setIdParque(int idParque) {
        this.idParque = idParque;
    }

    public abstract void navigateToActivity(Context context);
}

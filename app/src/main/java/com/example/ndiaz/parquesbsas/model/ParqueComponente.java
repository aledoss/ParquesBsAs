package com.example.ndiaz.parquesbsas.model;

import android.content.Context;

import com.example.ndiaz.parquesbsas.model.ParqueComponentes.ParqueComponenteJacksonMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ParqueComponenteJacksonMapper.class)
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

    public void setParqueComponente(ParqueComponente parqueComponente) {
        this.nombreComponente = parqueComponente.getNombreComponente();
        this.idParque = parqueComponente.getIdParque();
    }
}

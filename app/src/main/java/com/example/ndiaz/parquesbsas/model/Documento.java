package com.example.ndiaz.parquesbsas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Lenwe on 26/10/2017.
 */

public class Documento {

    @JsonProperty("id_tipo_documento")
    private int id;
    @JsonProperty("descripcion")
    private String tipoDocumento;
    @JsonProperty("numero_documento")
    private String numeroDocumento;

    public Documento() {
    }

    public Documento(int id, String tipoDocumento) {
        this.id = id;
        this.tipoDocumento = tipoDocumento;
    }

    public Documento(int id, String tipoDocumento, String numeroDocumento) {
        this.id = id;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
    }

    public int getId() {
        return id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}

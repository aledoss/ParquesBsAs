package com.example.ndiaz.parquesbsas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Lenwe on 26/10/2017.
 */

public class TiposDocumento {

    @JsonProperty("id_tipo_documento")
    private int id;
    @JsonProperty("descripcion")
    private String tipoDocumento;

    public TiposDocumento() {
    }

    public TiposDocumento(int id, String tipoDocumento) {
        this.id = id;
        this.tipoDocumento = tipoDocumento;
    }

    public int getId() {
        return id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}

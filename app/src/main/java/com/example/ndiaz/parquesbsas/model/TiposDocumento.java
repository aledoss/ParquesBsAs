package com.example.ndiaz.parquesbsas.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lenwe on 26/10/2017.
 */

public class TiposDocumento {

    @SerializedName("id_tipo_documento")
    @Expose
    private int id;
    @SerializedName("descripcion")
    @Expose
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

package com.example.ndiaz.parquesbsas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CalificacionEncuesta {

    @JsonProperty("id_calificacion")
    private int idCalificacion;
    @JsonProperty("id_encuesta")
    private int idEncuesta;
    @JsonProperty("id_parque")
    private int idParque;
    @JsonProperty("id_usuario")
    private int idUsuario;

    public CalificacionEncuesta(int idCalificacion, int idEncuesta, int idParque, int idUsuario) {
        this.idCalificacion = idCalificacion;
        this.idEncuesta = idEncuesta;
        this.idParque = idParque;
        this.idUsuario = idUsuario;
    }
}

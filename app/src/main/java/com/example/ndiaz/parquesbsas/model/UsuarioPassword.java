package com.example.ndiaz.parquesbsas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioPassword extends Usuario {

    @JsonProperty("contrasenia_vieja")
    private String oldPassword;

    public UsuarioPassword(Integer idUsuario, String password, String oldPassword) {
        super(idUsuario, password);
        this.oldPassword = oldPassword;
    }

}

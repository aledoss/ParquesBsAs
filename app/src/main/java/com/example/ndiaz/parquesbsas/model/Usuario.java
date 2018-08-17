package com.example.ndiaz.parquesbsas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Usuario implements Serializable {

    @JsonProperty("id_usuario")
    private Integer id;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("apellido")
    private String apellido;
    @JsonProperty("numero_documento")
    private String numeroDoc;
    @JsonProperty("tipo_doc")
    private String tipoDoc;
    @JsonProperty("id_tipo_documento")
    private int idTipoDoc;
    @JsonProperty("email")
    private String email;
    @JsonProperty("contrasenia")
    private String password;
    @JsonProperty("googleId")
    private String googleId;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Usuario(Integer id, String password) {
        this.id = id;
        this.password = password;
    }

    public Usuario(String nombre, String apellido, String numeroDoc, int idTipoDoc, String tipoDoc, String email, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDoc = numeroDoc;
        this.tipoDoc = tipoDoc;
        this.idTipoDoc = idTipoDoc;
        this.email = email;
        this.password = password;
    }

    public Usuario(int id, String nombre, String apellido, String numeroDoc, int idTipoDoc, String tipoDoc, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDoc = numeroDoc;
        this.tipoDoc = tipoDoc;
        this.idTipoDoc = idTipoDoc;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumeroDoc() {
        return numeroDoc;
    }

    public void setNumeroDoc(String numeroDoc) {
        this.numeroDoc = numeroDoc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public int getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(int idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public boolean hasDocument() {
        return getNumeroDoc() != null && !getNumeroDoc().isEmpty();
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
}

package com.example.ndiaz.parquesbsas.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuario implements Serializable {

    @SerializedName("id_usuario")
    @Expose
    private Integer id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("numero_documento")
    @Expose
    private String numeroDoc;
    @SerializedName("tipo_doc")
    @Expose
    public String tipoDoc;
    @SerializedName("id_tipo_documento")
    @Expose
    private int idTipoDoc;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("contrasenia")
    @Expose
    private String password;

    public Usuario() {
    }

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Usuario(String nombre, String apellido, String numeroDoc, String tipoDoc, String email, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDoc = numeroDoc;
        this.tipoDoc = tipoDoc;
        this.email = email;
        this.password = password;
    }

    public Usuario(String nombre, String apellido, String numeroDoc, int idTipoDoc, String email, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDoc = numeroDoc;
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
}

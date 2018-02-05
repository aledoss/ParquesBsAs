package com.example.ndiaz.parquesbsas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Reclamo implements Serializable {

    @JsonProperty("id_reclamo")
    private int idReclamo;
    @JsonProperty("id_parque")
    private Integer idParque;
    @JsonProperty("id_usuario")
    private Integer idUsuario;
    @JsonProperty("descripcion")
    private String nombre;
    @JsonProperty("comentarios")
    private String comentarios;
    @JsonProperty("fecha_creacion")
    private String fechaCreacion;
    @JsonProperty("latitud")
    private String latitud;
    @JsonProperty("longitud")
    private String longitud;
    @JsonProperty("imagen")
    private String imagen;
    @JsonProperty("cantidad")
    private Integer cantidad;
    @JsonProperty("nombre_parque")
    private String nombreParque;
    @JsonProperty("estado")
    private String estado;
    @JsonProperty("color")
    private String colorEstado;

    public int getIdReclamo() {
        return idReclamo;
    }

    public void setIdReclamo(int idReclamo) {
        this.idReclamo = idReclamo;
    }

    public Integer getIdParque() {
        return idParque;
    }

    public void setIdParque(Integer idParque) {
        this.idParque = idParque;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreParque() {
        return nombreParque;
    }

    public void setNombreParque(String nombreParque) {
        this.nombreParque = nombreParque;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getColorEstado() {
        return colorEstado;
    }

    public void setColorEstado(String colorEstado) {
        this.colorEstado = colorEstado;
    }

    public static String[] toArray(List<Reclamo> reclamos) {
        String[] descReclamos = new String[reclamos.size()];

        for (int i = 0; i < reclamos.size(); i++) {
            descReclamos[i] = reclamos.get(i).nombre;
        }

        return descReclamos;
    }
}

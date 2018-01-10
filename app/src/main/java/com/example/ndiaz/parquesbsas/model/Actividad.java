package com.example.ndiaz.parquesbsas.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Actividad implements Parcelable {

    @JsonProperty("id_actividad")
    private int id;
    private String nombre;
    @JsonProperty("desde")
    private String horaDesde;
    @JsonProperty("hasta")
    private String horaHasta;
    private String dia;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHoraDesde() {
        return horaDesde;
    }

    public String getHoraHasta() {
        return horaHasta;
    }

    public String getDia() {
        return dia;
    }

    public Actividad() {
        //Required for parcelabe
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nombre);
        dest.writeString(this.horaDesde);
        dest.writeString(this.horaHasta);
        dest.writeString(this.dia);
    }

    protected Actividad(Parcel in) {
        this.id = in.readInt();
        this.nombre = in.readString();
        this.horaDesde = in.readString();
        this.horaHasta = in.readString();
        this.dia = in.readString();
    }

    public static final Creator<Actividad> CREATOR = new Creator<Actividad>() {
        @Override
        public Actividad createFromParcel(Parcel source) {
            return new Actividad(source);
        }

        @Override
        public Actividad[] newArray(int size) {
            return new Actividad[size];
        }
    };
}

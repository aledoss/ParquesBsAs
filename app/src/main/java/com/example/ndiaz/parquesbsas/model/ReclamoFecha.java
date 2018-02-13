package com.example.ndiaz.parquesbsas.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReclamoFecha implements Parcelable {

    private Reclamo reclamo;
    private String fecha;

    public ReclamoFecha() {
        //Required for parcelable
    }

    public Reclamo getReclamo() {
        return reclamo;
    }

    public void setReclamo(Reclamo reclamo) {
        this.reclamo = reclamo;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public String getFormattedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(this.fecha));
            String dia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String mes = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            String año = String.valueOf(calendar.get(Calendar.YEAR));

            fechaFormateada = dia + "-" + mes + "-" + año;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return fechaFormateada;
    }

    public boolean isFecha() {
        return fecha != null && !fecha.isEmpty();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.reclamo, flags);
        dest.writeString(this.fecha);
    }

    protected ReclamoFecha(Parcel in) {
        this.reclamo = in.readParcelable(Reclamo.class.getClassLoader());
        this.fecha = in.readString();
    }

    public static final Creator<ReclamoFecha> CREATOR = new Creator<ReclamoFecha>() {
        @Override
        public ReclamoFecha createFromParcel(Parcel source) {
            return new ReclamoFecha(source);
        }

        @Override
        public ReclamoFecha[] newArray(int size) {
            return new ReclamoFecha[size];
        }
    };
}

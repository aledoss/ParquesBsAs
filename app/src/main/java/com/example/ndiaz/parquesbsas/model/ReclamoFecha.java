package com.example.ndiaz.parquesbsas.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReclamoFecha {

    private Reclamo reclamo;
    private String fecha;

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

}

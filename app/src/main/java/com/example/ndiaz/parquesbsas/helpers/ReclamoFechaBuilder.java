package com.example.ndiaz.parquesbsas.helpers;

import com.example.ndiaz.parquesbsas.model.Reclamo;
import com.example.ndiaz.parquesbsas.model.ReclamoFecha;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReclamoFechaBuilder {

    public ReclamoFechaBuilder() {
    }

    public List<ReclamoFecha> build(List<Reclamo> reclamos) {
        List<ReclamoFecha> reclamosFechas = new ArrayList<>();
        String diaCreacionAnterior = "";

        for (int i = 0; i < reclamos.size(); i++) {
            ReclamoFecha encabezadoReclamoFecha = new ReclamoFecha();
            ReclamoFecha reclamo = new ReclamoFecha();
            String fechaCreacionActual = reclamos.get(i).getFechaCreacion();

            if (!getDia(fechaCreacionActual).equalsIgnoreCase(diaCreacionAnterior)) {
                encabezadoReclamoFecha.setFecha(fechaCreacionActual);
            }
            reclamo.setReclamo(reclamos.get(i));

            diaCreacionAnterior = getDia(fechaCreacionActual);
            agregarALaLista(encabezadoReclamoFecha, reclamo, reclamosFechas);
        }

        return reclamosFechas;
    }

    private void agregarALaLista(ReclamoFecha encabezadoReclamoFecha, ReclamoFecha reclamo,
                                 List<ReclamoFecha> reclamosFechas) {
        if(encabezadoReclamoFecha.getFecha() != null){
            reclamosFechas.add(encabezadoReclamoFecha);
        }
        reclamosFechas.add(reclamo);
    }

    private String getDia(String fechaCreacion) {
        // TODO: 04/02/2018 Arreglar, ver de manejar la fecha con unix
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        String dia = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(fechaCreacion));
            dia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dia;
    }

}

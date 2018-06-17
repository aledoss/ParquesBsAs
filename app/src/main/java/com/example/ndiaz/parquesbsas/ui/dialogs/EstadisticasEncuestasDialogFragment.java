package com.example.ndiaz.parquesbsas.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.model.Calificacion;
import com.example.ndiaz.parquesbsas.model.Encuesta;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class EstadisticasEncuestasDialogFragment extends DialogFragment {

    private Encuesta encuesta;
    private Calificacion calificacion;
    private PieChart chart;

    public static EstadisticasEncuestasDialogFragment newInstance(Encuesta encuesta, Calificacion calificacion) {

        EstadisticasEncuestasDialogFragment fragment = new EstadisticasEncuestasDialogFragment();
        fragment.encuesta = encuesta;
        fragment.calificacion = calificacion;

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_estadisticas_encuesta, null);
        builder.setView(view);
        //builder.setTitle(encuesta.getNombre());
        this.initializeChart(view);

        return builder.create();
    }

    private void initializeChart(View view) {
        // TODO: 17/06/2018 Mejorar estilo
        chart = view.findViewById(R.id.estadisticasEncuestasChart);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(calificacion.getPorcBueno(), "Bueno"));
        entries.add(new PieEntry(calificacion.getPorcRegular(), "Regular"));
        entries.add(new PieEntry(calificacion.getPorcMalo(), "Malo"));

        PieDataSet dataSet = new PieDataSet(entries, "Calificaciones");
        PieData data = new PieData(dataSet);
        chart.setData(data);
        chart.setUsePercentValues(true);
        chart.setDrawSlicesUnderHole(true);
        chart.setEntryLabelTextSize(14f);

        chart.invalidate(); // refresh
    }
}

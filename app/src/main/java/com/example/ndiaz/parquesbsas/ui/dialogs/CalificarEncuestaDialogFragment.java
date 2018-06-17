package com.example.ndiaz.parquesbsas.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.listeners.OnCalificarEncuestaListener;
import com.example.ndiaz.parquesbsas.model.Calificacion;
import com.example.ndiaz.parquesbsas.model.Encuesta;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by nicolas on 12/06/18.
 */

public class CalificarEncuestaDialogFragment extends DialogFragment {

    @BindView(R.id.spEncuestas)
    Spinner spEncuestas;
    @BindView(R.id.spCalificaciones)
    Spinner spCalificaciones;
    @BindView(R.id.txtErrorMsg)
    TextView txtErrorMsg;

    private List<Encuesta> encuestas;
    private List<Calificacion> calificaciones;
    private Unbinder unbinder;
    private OnCalificarEncuestaListener onCalificarEncuestaListener;

    public static CalificarEncuestaDialogFragment newInstance(List<Encuesta> encuestas, List<Calificacion> calificaciones,
                                                              OnCalificarEncuestaListener onCalificarEncuestaListener) {
        CalificarEncuestaDialogFragment fragment = new CalificarEncuestaDialogFragment();

        fragment.encuestas = encuestas;
        fragment.calificaciones = calificaciones;
        fragment.onCalificarEncuestaListener = onCalificarEncuestaListener;

        return fragment;
    }

    @OnClick(R.id.btnCalificar)
    public void onCalificarEncuesta() {
        txtErrorMsg.setVisibility(View.GONE);
        onCalificarEncuestaListener.onCalificarEncuesta(
                encuestas.get(spEncuestas.getSelectedItemPosition()),
                calificaciones.get(spCalificaciones.getSelectedItemPosition())
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setupSpinnerEncuestas() {
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), R.layout.spinner_encuesta_item,
                getEncuestasDesc());
        spEncuestas.setAdapter(adapter);
    }

    private void setupSpinnerCalificaciones() {
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), R.layout.spinner_encuesta_item,
                getCalificacionesDesc());
        spCalificaciones.setAdapter(adapter);
    }

    private String[] getEncuestasDesc() {
        String[] encuestasArray = new String[encuestas.size()];

        for (int i = 0; i < encuestas.size(); i++) {
            encuestasArray[i] = encuestas.get(i).getNombre();
        }

        return encuestasArray;
    }

    private String[] getCalificacionesDesc() {
        String[] calificacionesArray = new String[calificaciones.size()];

        for (int i = 0; i < calificaciones.size(); i++) {
            calificacionesArray[i] = calificaciones.get(i).getNombre();
        }

        return calificacionesArray;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_calificar_encuesta, null);
        builder.setView(view);

        return builder.create();
    }

    private void initializeViews() {
        setupSpinnerEncuestas();
        setupSpinnerCalificaciones();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (unbinder == null) {
            unbinder = ButterKnife.bind(this, getDialog());
            initializeViews();
        }
    }

    public void onFailInsertCalificacionEncuesta(String message) {
        txtErrorMsg.setVisibility(View.VISIBLE);
        txtErrorMsg.setText(message);
    }
}

package com.example.ndiaz.parquesbsas.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.helpers.maps.IntentMap;
import com.example.ndiaz.parquesbsas.model.Reclamo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.ndiaz.parquesbsas.model.Reclamo.DEFAULT_LAT_LNG_VALUE;
import static com.example.ndiaz.parquesbsas.model.Reclamo.RECLAMO_KEY;

public class ReclamoDialogFragment extends DialogFragment {

    @BindView(R.id.txtNombreParque)
    TextView txtNombreParque;
    @BindView(R.id.txtFechaCreacion)
    TextView txtFechaCreacion;
    @BindView(R.id.txtEstadoReclamo)
    TextView txtEstadoReclamo;
    @BindView(R.id.txtComentarios)
    TextView txtComentarios;
    @BindView(R.id.viewEstado)
    View viewEstado;

    private Reclamo reclamo;
    private Unbinder unbinder;
    private IntentMap intentMap;

    public static ReclamoDialogFragment newInstance(Reclamo reclamo) {

        Bundle args = new Bundle();

        ReclamoDialogFragment fragment = new ReclamoDialogFragment();
        args.putParcelable(RECLAMO_KEY, reclamo);
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_reclamo, null);
        builder.setView(view);
        initializeVariables();
        initializeButtons(builder);

        return builder.create();
    }

    private void initializeVariables() {
        Bundle bundle = getArguments();
        reclamo = bundle.getParcelable(RECLAMO_KEY);
        intentMap = new IntentMap(getContext());
    }

    private void initializeButtons(AlertDialog.Builder builder) {
        // TODO: 13/02/2018 Los listeners de las acciones del botón
        if (!reclamo.getImagen().isEmpty()) {
            builder.setPositiveButton(getString(R.string.image), null);
        }

        if (!reclamo.getLatitud().isEmpty() && !reclamo.getLatitud().equalsIgnoreCase(DEFAULT_LAT_LNG_VALUE)) {
            builder.setNegativeButton(getString(R.string.map), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intentMap.navigateToMapsWithMarker(reclamo.getLatitud(), reclamo.getLongitud(), reclamo.getNombre());
                }
            });
        }

        builder.setNeutralButton(getString(R.string.share), null);
    }

    private void initializeViews() {
        txtNombreParque.setText(reclamo.getNombreParque());
        txtFechaCreacion.setText(reclamo.getFechaCreacion());
        txtEstadoReclamo.setText(reclamo.getEstado());
        txtComentarios.setText(reclamo.getComentarios());

        Drawable background = viewEstado.getBackground();
        ((GradientDrawable) background).setColor(Color.parseColor("#" + reclamo.getColorEstado()));
    }

    @Override
    public void onStart() {
        super.onStart();
        unbinder = ButterKnife.bind(this, getDialog());
        initializeViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

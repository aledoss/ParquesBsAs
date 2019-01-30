package com.example.ndiaz.parquesbsas.ui.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.BuildConfig;
import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.helpers.maps.IntentMap;
import com.example.ndiaz.parquesbsas.model.Reclamo;
import com.example.ndiaz.parquesbsas.ui.activities.reclamos.ImagenReclamoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.ndiaz.parquesbsas.model.Reclamo.DEFAULT_LAT_LNG_VALUE;

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
        ReclamoDialogFragment fragment = new ReclamoDialogFragment();
        fragment.reclamo = reclamo;
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
        intentMap = new IntentMap(getContext());
    }

    private void initializeButtons(AlertDialog.Builder builder) {
        if (!reclamo.getImagen().isEmpty()) {
            builder.setPositiveButton(R.string.image,
                    (dialog, which) -> ImagenReclamoActivity.newIntent(getContext(), reclamo.getImagen()));
        }

        if (!reclamo.getLatitud().isEmpty() && !reclamo.getLatitud().equalsIgnoreCase(DEFAULT_LAT_LNG_VALUE)) {
            builder.setNegativeButton(R.string.map,
                    (dialog, which) -> intentMap.navigateToMapsWithMarker(reclamo.getLatitud(), reclamo.getLongitud(), reclamo.getNombre()));
        }

        builder.setNeutralButton(R.string.share, ((dialog, which) -> shareReclamo()));
    }

    private void shareReclamo() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, getTextToShare());
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Compartir reclamo"));
    }

    private String getTextToShare() {
        return "Realicé mi reclamo mediante la app Parques BsAs" +
                "Parque: " + reclamo.getNombreParque() +
                ", Reclamo: " + reclamo.getNombre() +
                ", Comentarios: " + reclamo.getComentarios() +
                ". Realizá el tuyo ingresando a: " + BuildConfig.HOME_URL;
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

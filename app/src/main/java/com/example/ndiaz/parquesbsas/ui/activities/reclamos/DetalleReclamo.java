package com.example.ndiaz.parquesbsas.ui.activities.reclamos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.model.Reclamo;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ndiaz.parquesbsas.helpers.Constants.RECLAMODETALLES;

public class DetalleReclamo extends AppCompatActivity {

    @BindView(R.id.txt_reclamo_nombre_parque_detalle)
    TextView txtNombreParque;
    @BindView(R.id.txt_reclamo_nombre_detalle)
    TextView txtNombreReclamo;
    @BindView(R.id.txt_reclamo_comentarios_detalle)
    TextView txtComentarios;
    @BindView(R.id.txt_reclamo_latitud_detalle)
    TextView txtLatitud;
    @BindView(R.id.txt_reclamo_longitud_detalle)
    TextView txtLongitud;
    @BindView(R.id.txt_reclamo_fecha_creacion_detalle)
    TextView txtFechaCreacion;
    @BindView(R.id.toolbar_detalle_reclamo)
    Toolbar toolbar;
    Reclamo reclamo;
    String nombreParque = "", nombreReclamo = "", comentarios = "", latitud = "", longitud = "", fechaCreacion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reclamo);
        obtenerReclamo();   //obtengo el objeto reclamo (que viene del intent anterior) y asigno las variables
        setupUI();  //setup de la toolbar y los textview
    }

    private void obtenerReclamo() {
        try {
            reclamo = (Reclamo) getIntent().getExtras().getSerializable(RECLAMODETALLES);
            nombreParque = reclamo.getParque();
            nombreReclamo = reclamo.getNombre();
            comentarios = reclamo.getComentarios();
            latitud = reclamo.getLatitud();
            longitud = reclamo.getLongitud();
            fechaCreacion = reclamo.getFechaCreacion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupUI() {
        ButterKnife.bind(this);
        setupToolbar();
        txtNombreParque.setText(nombreParque);
        txtNombreReclamo.setText(nombreReclamo);
        txtComentarios.setText(comentarios);
        if (conLatLng()) {
            txtLatitud.setText(latitud);
            txtLongitud.setText(longitud);
        } else {
            txtLatitud.setText("Reclamo sin latitud, ni longitud");
            txtLongitud.setVisibility(View.GONE);
        }
        txtFechaCreacion.setText(fechaCreacion);
    }

    private boolean conLatLng() {
        if (latitud.equalsIgnoreCase("0.0")) {
            return false;
        } else {
            return true;
        }
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.detalle_reclamo);
    }
}

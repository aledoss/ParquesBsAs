package com.example.ndiaz.parquesbsas.ui.activities.old;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.constants.Constants;
import com.example.ndiaz.parquesbsas.database.DBHelper;
import com.example.ndiaz.parquesbsas.model.Parque;

public class AgregarParque extends AppCompatActivity implements Constants {

    private EditText etNombre, etDescCorta, etDesc, etLatitud, etLongitud;
    private Button btnAgregar;
    private String nombre, descCorta, desc, latitud, longitud;
    private Spinner spinnerLat, spinnerLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_parque);
        setupUI();
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerDatos();
                if (datosNoVacios()) {
                    Parque parque = crearParque();
                    Toast.makeText(AgregarParque.this, "parque creado", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AgregarParque.this, "Datos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Parque crearParque() {
        DBHelper db = new DBHelper(this);
        Parque parque = new Parque();
        parque.setNombre(nombre);
        //parque.setDescripcionCorta(descCorta);
        parque.setDescripcion(desc);
        parque.setLatitud(latitud);
        parque.setLongitud(longitud);
        parque.setImagen("http://caballitotequiero.com.ar/portal/wp-content/uploads/2016/06/3-8.jpg");  //imagen por defecto
        //db.insertarParque(parque);
        db.close();
        return parque;
    }

    private boolean datosNoVacios() {
        if (nombre.equalsIgnoreCase("") || descCorta.equalsIgnoreCase("") || desc.equalsIgnoreCase("")
                /*|| latitud.equalsIgnoreCase("") || longitud.equalsIgnoreCase("")*/) {
            return false;
        } else {
            return true;
        }

    }

    private void obtenerDatos() {
        nombre = etNombre.getText().toString();
        descCorta = etDescCorta.getText().toString();
        desc = etDesc.getText().toString();
        //latitud = etLatitud.getText().toString();
        //longitud = etLongitud.getText().toString();
    }

    private void setupUI() {
        etNombre = (EditText) findViewById(R.id.etNombreParqueCrear);
        etDescCorta = (EditText) findViewById(R.id.etDescCortaParqueCrear);
        etDesc = (EditText) findViewById(R.id.etDescParqueCrear);
        spinnerLat = (Spinner) findViewById(R.id.spinner_lat);
        spinnerLng = (Spinner) findViewById(R.id.spinner_lng);
        spinnerLat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                latitud = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerLng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                longitud = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //etLatitud = (EditText) findViewById(R.id.etLatitudParqueCrear);
        //etLongitud = (EditText) findViewById(R.id.etLongitudParqueCrear);
        btnAgregar = (Button) findViewById(R.id.btnAgregarParque);
    }
}

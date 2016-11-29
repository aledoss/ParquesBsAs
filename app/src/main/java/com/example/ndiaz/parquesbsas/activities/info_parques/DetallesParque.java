package com.example.ndiaz.parquesbsas.activities.info_parques;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.activities.reclamos.AgregarReclamo;
import com.example.ndiaz.parquesbsas.database.Parque;
import com.example.ndiaz.parquesbsas.util.async_tasks.XMLParserEcoBici;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetallesParque extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_desc_general_parque_detalle)
    Button btnDescGral;
    @BindView(R.id.btn_actividad_parque_detalle)
    Button btnActividad;
    @BindView(R.id.btn_feria_parque_detalle)
    Button btnFeria;
    @BindView(R.id.btn_eco_bici_parque_detalle)
    Button btnEcobici;
    @BindView(R.id.btn_est_saludable_parque_detalle)
    Button btnEstSaludableº;
    @BindView(R.id.toolbar_parque_detalles)
    Toolbar toolbar;
    protected XmlPullParserFactory xmlPullParserFactory;
    protected XmlPullParser parser;
    Parque parque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_parque);
        setupXMLParser();
        setupUI();
        obtenerDatosParque();

    }

    private void setupXMLParser() {
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(false);
            parser = xmlPullParserFactory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private void setupUI() {
        ButterKnife.bind(this);
        setupToolbar();
        btnEcobici.setOnClickListener(this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("nombre del parque");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void obtenerDatosParque() {
        try {
            Intent intent = getIntent();
            //parque = intent.getSerializableExtra();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_eco_bici_parque_detalle:
                //llamo al asynctask para obtener el xml (parsearlo), y mostrar en un dialog los datos de la ecobici
                XMLParserEcoBici xmlParserEcoBici = new XMLParserEcoBici(parser, DetallesParque.this);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detalles_parque_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.agregar_reclamo_menu:
                startActivity(new Intent(DetallesParque.this, AgregarReclamo.class));
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
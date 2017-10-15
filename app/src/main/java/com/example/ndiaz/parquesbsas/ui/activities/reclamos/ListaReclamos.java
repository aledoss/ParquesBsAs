package com.example.ndiaz.parquesbsas.ui.activities.reclamos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.database.DBHelper;
import com.example.ndiaz.parquesbsas.model.Reclamo;
import com.example.ndiaz.parquesbsas.ui.adapters.AdapterListaReclamos;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ndiaz.parquesbsas.constants.Constants.RECLAMODETALLES;

public class ListaReclamos extends AppCompatActivity {

    @BindView(R.id.toolbar_lista_reclamo)
    Toolbar toolbar;
    @BindView(R.id.list_view_reclamos)
    ListView listViewReclamos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reclamos);
        setupUI();
    }

    private void setupUI() {
        ButterKnife.bind(this);
        setupToolbar();
        listViewReclamos.setEmptyView(findViewById(R.id.txt_empty_reclamos));
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.lista_reclamos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupLista() {
        DBHelper db = new DBHelper(this);
        final ArrayList<Reclamo> listaReclamos = db.getAllReclamos();   //obtengo los reclamos de la db
        AdapterListaReclamos adapter = new AdapterListaReclamos(this, listaReclamos);   //creo el adapter
        listViewReclamos.setAdapter(adapter);   //muestro el adapter
        listViewReclamos.setOnItemClickListener(new AdapterView.OnItemClickListener() { //onclick lo llevo al detalle del reclamo tocado
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reclamo reclamo = listaReclamos.get(position);
                Intent intent = new Intent(ListaReclamos.this, DetalleReclamo.class);
                intent.putExtra(RECLAMODETALLES, (Serializable) reclamo);
                startActivity(intent);
            }
        });
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupLista();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

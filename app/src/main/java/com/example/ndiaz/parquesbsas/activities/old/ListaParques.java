package com.example.ndiaz.parquesbsas.activities.old;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.database.DBHelper;
import com.example.ndiaz.parquesbsas.database.Parque;
import com.example.ndiaz.parquesbsas.util.adapters.AdapterListaParques;
import com.example.ndiaz.parquesbsas.util.Constants;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaParques extends AppCompatActivity implements Constants {

    private ListView listViewParques;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_parques);
        setupUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupLista();
    }

    private void setupLista() {
        DBHelper db = new DBHelper(this);
        final ArrayList<Parque> listaParques = db.getAllParques();
        try {
            Log.d("ListaParques", "parque: ID: " + listaParques.get(0).getId() + " nombre " + listaParques.get(0).getNombre() +
                    " desc corta " + listaParques.get(0).getDescripcionCorta());
        } catch (Exception e) {
            e.printStackTrace();
        }
        AdapterListaParques adapter = new AdapterListaParques(this, listaParques);
        listViewParques.setAdapter(adapter);
        listViewParques.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Parque parque = listaParques.get(position);
                Intent intent = new Intent(ListaParques.this, DetallesParqueOld.class);
                intent.putExtra(PARQUEDETALLES, (Serializable) parque);
                startActivity(intent);
            }
        });
        db.close();
    }

    private void setupUI() {
        listViewParques = (ListView) findViewById(R.id.list_view_parques);
        toolbar = (Toolbar) findViewById(R.id.toolbar_lista_parques);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lista parques");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_parques_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.agregar_parque_menu:
                startActivity(new Intent(ListaParques.this, AgregarParque.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

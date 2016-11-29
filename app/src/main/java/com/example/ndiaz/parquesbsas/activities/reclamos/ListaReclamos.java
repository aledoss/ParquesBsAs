package com.example.ndiaz.parquesbsas.activities.reclamos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ndiaz.parquesbsas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaReclamos extends AppCompatActivity {

    @BindView(R.id.toolbar_lista_reclamo) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reclamos);
        setupUI();
    }

    private void setupUI() {
        ButterKnife.bind(this);
        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.lista_reclamos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_reclamos_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.agregar_reclamo_menu:
                startActivity(new Intent(ListaReclamos.this, AgregarReclamo.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

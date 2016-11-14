package com.example.ndiaz.parquesbsas.activities.reclamos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ndiaz.parquesbsas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgregarReclamo extends AppCompatActivity {

    private static int REQUEST_CODE_CAMERA = 123;
    @BindView(R.id.toolbar_agregar_reclamo)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_reclamo);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.agregar_reclamo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sacar_foto_reclamo_menu:
                startActivityForResult(new Intent(AgregarReclamo.this, CamaraReclamo.class), 123);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (resultCode == RESULT_OK) {
                Log.d("NICOTEST", "Resultado: " + data.toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

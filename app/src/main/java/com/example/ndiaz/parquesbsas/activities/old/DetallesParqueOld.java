package com.example.ndiaz.parquesbsas.activities.old;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.database.DBHelper;
import com.example.ndiaz.parquesbsas.database.Parque;
import com.example.ndiaz.parquesbsas.util.Constants;
import com.squareup.picasso.Picasso;

public class DetallesParqueOld extends AppCompatActivity implements Constants, View.OnClickListener {

    private Toolbar toolbar;
    private TextView txtNombre, txtDesc;
    private ImageView imgParque;
    private String nombre, desc, imagen;
    private Parque parque;
    private Button btnModificar, btnCancelar;
    private EditText etDesc;
    private TextView txtLatitud, txtLongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_parque_old);
        obtenerDatosParque();
        setupUI();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(nombre);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void obtenerDatosParque() {
        try {
            parque = (Parque) getIntent().getExtras().getSerializable(PARQUEDETALLES);
            nombre = parque.getNombre();
            desc = parque.getDescripcion();
            imagen = parque.getImagen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_parque_detalles);
        txtNombre = (TextView) findViewById(R.id.txt_detalle_parque_nombre);
        txtDesc = (TextView) findViewById(R.id.txt_detalle_parque_desc);
        imgParque = (ImageView) findViewById(R.id.img_detalle_parque);

        //al tocar "modificar"
        etDesc = (EditText) findViewById(R.id.et_detalle_parque_desc);
        btnCancelar = (Button) findViewById(R.id.btn_detalle_parque_cancelar);
        btnModificar = (Button) findViewById(R.id.btn_detalle_parque_modificar);
        btnCancelar.setOnClickListener(this);
        btnModificar.setOnClickListener(this);

        setupToolbar();
        Picasso.with(DetallesParqueOld.this)
                .load(imagen)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imgParque);
        txtNombre.setText(nombre);
        txtDesc.setText(desc);

        txtLatitud = (TextView) findViewById(R.id.txtLatitud);
        txtLongitud = (TextView) findViewById(R.id.txtLongitud);
        txtLatitud.setText(parque.getLatitud());
        txtLongitud.setText(parque.getLongitud());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detalles_parque_menu_old, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.modificar_parque_menu:
                btnModificar.setVisibility(View.VISIBLE);
                btnCancelar.setVisibility(View.VISIBLE);
                etDesc.setText(desc);
                etDesc.setVisibility(View.VISIBLE);
                txtDesc.setVisibility(View.GONE);
                break;
            case R.id.borrar_parque_menu:
                borrarParque();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void borrarParque() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetallesParqueOld.this);
        builder.setTitle(getResources().getString(R.string.borrar));
        builder.setMessage(getResources().getString(R.string.pregunta_borrar_parque));
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper db = new DBHelper(DetallesParqueOld.this);
                db.deleteParque(parque);
                db.close();
                finish();
            }
        });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_detalle_parque_modificar:
                DBHelper db = new DBHelper(DetallesParqueOld.this);
                parque.setDescripcion(etDesc.getText().toString());
                txtDesc.setText(parque.getDescripcion());
                db.updateParque(parque);
                btnCancelar.performClick();
                Toast.makeText(this, "Modificado", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_detalle_parque_cancelar:
                btnModificar.setVisibility(View.GONE);
                btnCancelar.setVisibility(View.GONE);
                etDesc.setVisibility(View.GONE);
                txtDesc.setVisibility(View.VISIBLE);
                break;
        }
    }
}

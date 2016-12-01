package com.example.ndiaz.parquesbsas.activities.reclamos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.database.DBHelper;
import com.example.ndiaz.parquesbsas.database.Parque;
import com.example.ndiaz.parquesbsas.database.Reclamo;
import com.example.ndiaz.parquesbsas.util.camara.PhotoHandler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ndiaz.parquesbsas.util.Constants.IMAGENBYTES;
import static com.example.ndiaz.parquesbsas.util.Constants.LASTLOCATIONLATITUD;

public class AgregarReclamo extends AppCompatActivity implements View.OnClickListener {

    private static int REQUEST_CODE_CAMERA = 123;
    @BindView(R.id.toolbar_agregar_reclamo)
    Toolbar toolbar;
    @BindView(R.id.txt_reclamo_nombre_parque)
    TextView txtNombreParque;
    @BindView(R.id.et_reclamos_comentarios)
    EditText etComentarios;
    @BindView(R.id.btn_generar_reclamo)
    Button btnGenerarReclamo;
    @BindView(R.id.btn_lista_reclamos)
    Button btnListaReclamos;
    Parque parque;
    boolean reclamoConFoto = false;
    String reclamoNombre = "", rutaImagen = "";
    byte[] mFotoReclamo;
    double latitud = 0, longitud = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_reclamo);
        obtenerDatosParque();
        setupUI();

    }

    private void obtenerDatosParque() {
        Intent intent = getIntent();
        //intent.getExtras().getSerializable();
        DBHelper db = new DBHelper(this);
        /*Parque parque = new Parque();
        parque.setId_parque(0);
        parque.setNombre("Parque Caballito");
        parque.setDescripcion("Parque ubicado en.......");
        parque.setDescripcionCorta("Desc corta");
        parque.setImagen("http://caballitotequiero.com.ar/portal/wp-content/uploads/2016/06/3-8.jpg");
        parque.setLatitud("-34.6060982");
        parque.setLongitud("-58.4354782");
        parque.setBarrio("Caballito");
        db.insertarParque(parque);*/
        ArrayList<Parque> listaParques = db.getAllParques();
        this.parque = listaParques.get(0);
        db.close();
    }

    private void setupUI() {
        ButterKnife.bind(this);
        setupToolbar();
        txtNombreParque.setText(parque.getNombre());
        btnListaReclamos.setOnClickListener(this);
        btnGenerarReclamo.setOnClickListener(this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.agregar_reclamo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                //inicio la activity de la camara y le mando el codigo por parametro para tomarlo
                startActivityForResult(new Intent(AgregarReclamo.this, CamaraReclamo.class), REQUEST_CODE_CAMERA);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (resultCode == RESULT_OK) {
                this.reclamoConFoto = true;
                //obtengo los bytes[] de la foto y la location
                mFotoReclamo = data.getByteArrayExtra(IMAGENBYTES);
                latitud = data.getDoubleExtra(LASTLOCATIONLATITUD, 0);
                longitud = data.getDoubleExtra(LASTLOCATIONLATITUD, 0);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_generar_reclamo:
                if (datosNoVacios()) {
                    if (reclamoConFoto) {   //si se saco una foto
                        //guardo la imagen en el celu y la subo al ftp
                        PhotoHandler handler = new PhotoHandler(getApplicationContext(), mFotoReclamo);
                        handler.procesarImagen();
                    }
                    insertarReclamoDB();
                    finish();
                }
                break;
            case R.id.btn_lista_reclamos:
                mostrarLista();
                break;
        }
    }

    private boolean datosNoVacios() {
        String comentarios = String.valueOf(etComentarios.getText());
        String elegirReclamoDefecto = getResources().getString(R.string.elegir_reclamo);
        if (comentarios.equalsIgnoreCase("") && btnListaReclamos.getText().toString().equalsIgnoreCase(elegirReclamoDefecto)) {
            Toast.makeText(this, "Completar reclamo y/o comentarios", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void insertarReclamoDB() {
        DBHelper db = new DBHelper(this);
        Reclamo reclamo = new Reclamo();
        reclamo.setNombre(reclamoNombre);  //el get que devuelve el contextmenu
        reclamo.setParque(parque.getNombre());
        reclamo.setComentarios(String.valueOf(etComentarios.getText()));
        if (reclamoConFoto) {   //si se saco una foto..
            reclamo.setLatitud(String.valueOf(latitud));
            reclamo.setLongitud(String.valueOf(longitud));
            reclamo.setImagen(String.valueOf(rutaImagen));
        }
        //db.insertarReclamo(reclamo);
        db.close();
    }

    private void mostrarLista() {
        final String[] listaReclamos = getResources().getStringArray(R.array.lista_reclamos);
        AlertDialog.Builder builder = new AlertDialog.Builder(AgregarReclamo.this);
        builder.setTitle(R.string.reclamos)
                .setItems(listaReclamos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        reclamoNombre = listaReclamos[position];
                        btnListaReclamos.setText(reclamoNombre);
                    }
                })
                .show();
    }
}

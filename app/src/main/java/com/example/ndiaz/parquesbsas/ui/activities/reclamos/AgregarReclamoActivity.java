package com.example.ndiaz.parquesbsas.ui.activities.reclamos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.AgregarReclamoContract;
import com.example.ndiaz.parquesbsas.edittextvalidator.reclamo.ReclamoFactoryEditText;
import com.example.ndiaz.parquesbsas.helpers.ViewHelper;
import com.example.ndiaz.parquesbsas.helpers.camara.PhotoHandler;
import com.example.ndiaz.parquesbsas.interactor.AgregarReclamoInteractor;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.Reclamo;
import com.example.ndiaz.parquesbsas.presenter.AgregarReclamoPresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.ndiaz.parquesbsas.constants.Constants.IMAGENBYTES;
import static com.example.ndiaz.parquesbsas.constants.Constants.LASTLOCATIONLATITUD;
import static com.example.ndiaz.parquesbsas.constants.Constants.LASTLOCATIONLONGITUD;

public class AgregarReclamoActivity extends BaseActivity<AgregarReclamoContract.Presenter>
        implements AgregarReclamoContract.View {

    private static int REQUEST_CODE_CAMERA = 123;

    @BindView(R.id.toolbar_agregar_reclamo)
    Toolbar toolbar;
    @BindView(R.id.txt_reclamo_nombre_parque)
    TextView txtNombreParque;
    @BindView(R.id.et_reclamos_comentarios)
    EditText etComentarios;
    @BindView(R.id.btn_generar_reclamo)
    Button btnGenerarReclamo;
    @BindView(R.id.btn_elegir_reclamo)
    Button btnListaReclamos;
    @BindView(R.id.lLContainer)
    LinearLayout lLContainer;

    private List<Reclamo> reclamos;
    private String[] reclamosDesc;
    private Parque parque;
    private ViewHelper viewHelper;
    boolean reclamoConFoto = false;
    String rutaImagen = "";
    byte[] mFotoReclamo;
    double latitud = 0, longitud = 0;

    @OnClick(R.id.btn_generar_reclamo)
    public void onBtnGenerarReclamo() {

        if (datosValidos()) {
            if (reclamoConFoto) {   //si se saco una foto
                //guardo la imagen en el celu y la subo al ftp
                PhotoHandler handler = new PhotoHandler(getApplicationContext(), mFotoReclamo);
                handler.procesarImagen();
            }
            presenter.doInsertReclamo(getDatosReclamo());
            //PhotoHandler.showNotif("Reclamo Insertado", this);
        }
    }

    @OnClick(R.id.btn_elegir_reclamo)
    public void onBtnElegirReclamo() {
        mostrarListaReclamos();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_reclamo);
        obtenerDatos();
        setupUI();
        initializeVariables();
    }

    @Override
    protected AgregarReclamoContract.Presenter createPresenter() {
        AgregarReclamoInteractor interactor = new AgregarReclamoInteractor(getNetworkServiceImp());

        return new AgregarReclamoPresenter(this, interactor);
    }

    private void obtenerDatos() {
        parque = ParquesApplication.getInstance().getParque();
    }

    private void setupUI() {
        setupToolbar();
        txtNombreParque.setText(parque.getNombre());
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.agregar_reclamo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeVariables() {
        viewHelper = new ViewHelper();
        if (reclamos != null && !reclamos.isEmpty()) {
            reclamosDesc = Reclamo.toArray(reclamos);
        } else {
            presenter.doGetReclamos();
        }
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
                startActivityForResult(new Intent(AgregarReclamoActivity.this, CamaraReclamo.class), REQUEST_CODE_CAMERA);
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
                longitud = data.getDoubleExtra(LASTLOCATIONLONGITUD, 0);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean datosValidos() {
        return viewHelper.isValidData(new ReclamoFactoryEditText(etComentarios, btnListaReclamos));
    }

    private void mostrarListaReclamos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AgregarReclamoActivity.this);
        builder.setTitle(R.string.reclamos)
                .setItems(reclamosDesc, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        btnListaReclamos.setText(reclamosDesc[position]);
                    }
                })
                .show();
    }

    private int getReclamoId() {
        int idReclamo = 1;
        for (Reclamo reclamo : reclamos) {
            if (reclamo.getNombre().equalsIgnoreCase(btnListaReclamos.getText().toString())) {
                idReclamo = reclamo.getIdReclamo();
            }
        }
        return idReclamo;
    }

    private Reclamo getDatosReclamo() {
        Reclamo reclamo = new Reclamo();

        int userId = ParquesApplication.getInstance().getUser().getId();
        reclamo.setIdUsuario(userId);
        reclamo.setIdParque(parque.getIdParque());
        reclamo.setIdReclamo(getReclamoId());
        reclamo.setComentarios(String.valueOf(etComentarios.getText()));
        reclamo.setLatitud(String.valueOf(latitud));
        reclamo.setLongitud(String.valueOf(longitud));
        reclamo.setImagen(String.valueOf(rutaImagen));  //si no se saco foto, la latitud, longitud y la ruta de la imagen, se ponen en 0 y ""

        return reclamo;
    }

    @Override
    public void setReclamos(List<Reclamo> reclamos) {
        this.reclamos = reclamos;
        this.reclamosDesc = Reclamo.toArray(reclamos);
        btnListaReclamos.setEnabled(true);
    }

    @Override
    public void showMessage(String message) {
        showMessage(lLContainer, message);
    }
}

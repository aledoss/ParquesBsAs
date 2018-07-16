package com.example.ndiaz.parquesbsas.ui.activities.reclamos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.ndiaz.parquesbsas.helpers.FileManager;
import com.example.ndiaz.parquesbsas.helpers.IntentCamera;
import com.example.ndiaz.parquesbsas.helpers.ViewHelper;
import com.example.ndiaz.parquesbsas.helpers.maps.ActualLocation;
import com.example.ndiaz.parquesbsas.helpers.permissions.PermissionsManager;
import com.example.ndiaz.parquesbsas.interactor.AgregarReclamoInteractor;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.Reclamo;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.presenter.AgregarReclamoPresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.example.ndiaz.parquesbsas.constants.Constants.MESSAGE;

public class AgregarReclamoActivity extends BaseActivity<AgregarReclamoContract.Presenter>
        implements AgregarReclamoContract.View {

    public static final int RESULT_CODE_RECLAMO = 1;
    public static final int RESULT_CODE_SIN_DOCUMENTO = 2;

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
    private Usuario usuario;
    private ViewHelper viewHelper;
    private IntentCamera intentCamera;
    private FileManager fileManager;
    private ActualLocation actualLocation;
    boolean reclamoConFoto = false;
    String rutaImagen = "";
    double latitud = 0, longitud = 0;

    @OnClick(R.id.btn_generar_reclamo)
    public void onBtnGenerarReclamo() {
        if (datosValidos()) {
            if (reclamoConFoto) {
                presenter.doInsertReclamoWithPhoto(getDatosReclamo());
            } else {
                doInsertReclamo();
            }
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
        validateUser();
        getPermissionsManager().askForLocationPermission(this);
        setupUI();
        initializeVariables();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualLocation.startLocationUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        actualLocation.stopLocationUpdates();
    }

    @Override
    protected AgregarReclamoContract.Presenter createPresenter() {
        AgregarReclamoInteractor interactor = new AgregarReclamoInteractor(getNetworkServiceImp());

        return new AgregarReclamoPresenter(this, interactor);
    }

    private void obtenerDatos() {
        usuario = ParquesApplication.getInstance().getUser();
        parque = ParquesApplication.getInstance().getParque();
    }

    private void validateUser() {
        if (!usuario.hasDocument()) {
            setResult(RESULT_CODE_SIN_DOCUMENTO);
            finish();
        }
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
        intentCamera = new IntentCamera(this);
        fileManager = new FileManager(this);
        viewHelper = new ViewHelper();
        actualLocation = new ActualLocation(this);

        if (reclamos != null && !reclamos.isEmpty()) {
            reclamosDesc = Reclamo.toArray(reclamos);
        } else {
            presenter.doGetReclamos();
        }
    }

    private void doInsertReclamo() {
        presenter.doInsertReclamo(getDatosReclamo());
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
                openCamera();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openCamera() {
        askPermissionsToOpenCamera();
        if (hasPermissionsToOpenCamera()) {
            File imageFile = fileManager.createImageFile();
            if (imageFile != null && imageFile.exists()) {
                intentCamera.navigateToCamera(imageFile);
            }
        }
    }

    private void askPermissionsToOpenCamera() {
        getPermissionsManager().askForCameraPermission(this);
    }

    private boolean hasPermissionsToOpenCamera() {
        return getPermissionsManager().hasCameraPermission()
                && getPermissionsManager().hasStoragePermission();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IntentCamera.REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            String fileName = fileManager.getImageFileName();
            if (fileName != null && !fileName.isEmpty()) {
                this.reclamoConFoto = true;
                this.rutaImagen = fileName;
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
                .setItems(reclamosDesc, (dialog, position) -> btnListaReclamos.setText(reclamosDesc[position]))
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

        reclamo.setIdUsuario(usuario.getId());
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

    @Override
    public void navegarAListaReclamos(String value) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MESSAGE, value);
        setResult(RESULT_CODE_RECLAMO, returnIntent);
        finish();
    }

    @Override
    public void showRetryUploadingPhoto(Reclamo reclamo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Hubo un inconveniente al querer cargar la foto. Desea generar el reclamo sin foto?")
                .setPositiveButton(getString(R.string.dialog_ok), (dialog, which) -> {
                    reclamo.setImagen("");
                    presenter.doInsertReclamo(reclamo);
                })
                .setNegativeButton(getString(R.string.dialog_cancel), (dialog, which) -> dialog.dismiss())
                .setNeutralButton(getString(R.string.dialog_reintent), (dialog, which) -> presenter.doInsertReclamoWithPhoto(reclamo))
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionsManager.ACCESS_CAMERA_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults[0] == PERMISSION_GRANTED && grantResults[1] == PERMISSION_GRANTED &&
                    grantResults[2] == PERMISSION_GRANTED) {
                openCamera();
            } else {
                showMessage(getString(R.string.default_permission_rejected));
            }
        } else if (requestCode == PermissionsManager.ACCESS_FINE_LOCATION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                actualLocation.startLocationUpdates();
            } else {
                showMessage("La ubicación se utiliza para saber precisamente dónde se encuentra el problema al momento de sacar la foto");
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}

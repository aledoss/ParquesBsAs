package com.example.ndiaz.parquesbsas.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.PerfilUsuarioContract;
import com.example.ndiaz.parquesbsas.edittextvalidator.usuario.UserFactoryEditText;
import com.example.ndiaz.parquesbsas.helpers.TipoDocumentoHelper;
import com.example.ndiaz.parquesbsas.helpers.ViewHelper;
import com.example.ndiaz.parquesbsas.interactor.PerfilUsuarioInteractor;
import com.example.ndiaz.parquesbsas.listeners.OnCambiarContraseniaListener;
import com.example.ndiaz.parquesbsas.listeners.OnCambiarNombYApeListener;
import com.example.ndiaz.parquesbsas.model.Documento;
import com.example.ndiaz.parquesbsas.model.UsuarioPassword;
import com.example.ndiaz.parquesbsas.presenter.PerfilUsuarioPresenter;
import com.example.ndiaz.parquesbsas.ui.dialogs.CambiarContraseniaDialogFragment;
import com.example.ndiaz.parquesbsas.ui.dialogs.CambiarNombYApeDialogFragment;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.ndiaz.parquesbsas.edittextvalidator.usuario.UserFactoryEditText.UPDATE_USER_DOC_ORIGIN;

public class PerfilUsuarioActivity extends BaseActivity<PerfilUsuarioContract.Presenter>
        implements PerfilUsuarioContract.View, OnCambiarNombYApeListener, OnCambiarContraseniaListener {

    @BindView(R.id.createAccountContainerLayout)
    ConstraintLayout createAccountContainerLayout;
    @BindView(R.id.toolbar_perfil_usuario)
    Toolbar toolbar;
    @BindView(R.id.spiDocType)
    Spinner spiDocType;

    @BindView(R.id.txtMail)
    TextView txtMail;
    @BindView(R.id.txtNombreApellido)
    TextView txtNombreApellido;
    @BindView(R.id.etDocumento)
    EditText etDocumento;

    @BindView(R.id.imgEditNombre)
    ImageButton imgEditNombre;
    @BindView(R.id.imgEditDoc)
    ImageButton imgEditDoc;
    @BindView(R.id.imgCommitEditDoc)
    ImageButton imgCommitEditDoc;
    @BindView(R.id.imgDismissEditDoc)
    ImageButton imgDismissEditDoc;

    private List<Documento> documentos;
    private TipoDocumentoHelper tipoDocumentoHelper;
    private ArrayAdapter<String> docTypesAdapter;
    private boolean docEditionInProgress;
    private ViewHelper viewHelper;

    @OnClick(R.id.imgEditDoc)
    public void onEditDocClick() {
        if (this.documentos != null && !this.documentos.isEmpty()) {
            enableDocEdition();
        } else {
            showMessage("Por el momento no se puede realizar esta acción. Intente más tarde.");
        }
    }

    @OnClick(R.id.imgCommitEditDoc)
    public void onCommitEditDoc() {
        String docType = spiDocType.getSelectedItem().toString();
        if (datosDocumentoCorrectos(docType)) {
            disableDocEdition();
            int idDoc = tipoDocumentoHelper.getDocIdFromText(docType, documentos);
            String numeroDoc = etDocumento.getText().toString();

            presenter.doUpdateDoc(getUsuario().getId(), new Documento(idDoc, docType, numeroDoc));
        }
    }

    @OnClick(R.id.imgDismissEditDoc)
    public void onDismissEditDoc() {
        disableDocEdition();
        fillSpinner(new String[]{getUsuario().getTipoDoc()});
        etDocumento.setText(getUsuario().getNumeroDoc());
        etDocumento.setError(null);
    }

    @OnClick(R.id.imgEditNombre)
    public void onEditNombreClick() {
        CambiarNombYApeDialogFragment.newInstance(getUsuario(), this)
                .show(getSupportFragmentManager(), CambiarNombYApeDialogFragment.class.getSimpleName());
    }

    @OnClick(R.id.txtCambiarContrasenia)
    public void onCambiarContraseniaClick() {
        CambiarContraseniaDialogFragment.newInstance(this)
                .show(getSupportFragmentManager(), CambiarContraseniaDialogFragment.class.getSimpleName());
    }

    @OnClick(R.id.txtCerrarSesion)
    public void onCerrarSesionClick() {
        // TODO: 09/07/2018 Limpiar preferences
        /*try {
            SharedPreferences sharedPreferences = getSharedPreferences(LOGINPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(EMAILLOGINSAVED, "");
            editor.putString(PASSWORDLOGINSAVED, "");
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        navigateToLogin();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        initializeVariables();
        presenter.doGetDocTypes();
        setupUI();
    }

    private void initializeVariables() {
        tipoDocumentoHelper = new TipoDocumentoHelper();
        viewHelper = new ViewHelper();
    }

    @Override
    protected PerfilUsuarioContract.Presenter createPresenter() {
        PerfilUsuarioContract.Interactor interactor = new PerfilUsuarioInteractor(getNetworkServiceImp());

        return new PerfilUsuarioPresenter(this, interactor);
    }

    private void setupUI() {
        initializeToolbar();
        disableDocEdition();
        initializeTexts();
        initializeSpinner();
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.perfil));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeTexts() {
        txtMail.setText(getUsuario().getEmail());
        setNombreApellidoTxt(getUsuario().getNombre(), getUsuario().getApellido());
        etDocumento.setText(getUsuario().getNumeroDoc());
    }

    private void initializeSpinner() {
        fillSpinner(new String[]{getUsuario().getTipoDoc()});
    }

    private void enableDocEdition() {
        spiDocType.setEnabled(true);
        etDocumento.setEnabled(true);
        imgEditDoc.setVisibility(View.GONE);
        imgCommitEditDoc.setVisibility(View.VISIBLE);
        imgDismissEditDoc.setVisibility(View.VISIBLE);
        fillSpinner(tipoDocumentoHelper.getDocTypeArray(documentos));
        docEditionInProgress = true;
    }

    private void disableDocEdition() {
        spiDocType.setEnabled(false);
        etDocumento.setEnabled(false);
        imgEditDoc.setVisibility(View.VISIBLE);
        imgCommitEditDoc.setVisibility(View.GONE);
        imgDismissEditDoc.setVisibility(View.GONE);
        docEditionInProgress = false;
    }

    @Override
    public void showDocTypesError(String message) {
        disableDocEdition();
        showMessage(message);
    }

    @Override
    public void setDocTypesList(List<Documento> documentos) {
        this.documentos = documentos;
    }

    @Override
    public void fillSpinner(String[] docTypes) {
        docTypesAdapter = new ArrayAdapter(this, R.layout.spinner_doctype_item, docTypes);
        spiDocType.setAdapter(docTypesAdapter);
    }

    @Override
    public void updateName(String nombre, String apellido) {
        setNombreApellidoTxt(nombre, apellido);
        getUsuario().setNombre(nombre);
        getUsuario().setApellido(apellido);
    }

    @Override
    public void updateDoc(Documento documento) {
        fillSpinner(new String[]{documento.getTipoDocumento()});
        etDocumento.setText(documento.getNumeroDocumento());
        getUsuario().setIdTipoDoc(documento.getId());
        getUsuario().setTipoDoc(documento.getNumeroDocumento());
        getUsuario().setNumeroDoc(documento.getTipoDocumento());
    }

    @Override
    public void showSuccessPasswordUpdated() {
        showMessage("Su contraseña ha sido actualizada.");
    }

    @Override
    public void showMessage(String message) {
        showMessage(createAccountContainerLayout, message);
    }

    @Override
    public void onBackPressed() {
        if (docEditionInProgress) {
            showExitDialog();
        } else {
            super.onBackPressed();
        }
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cambios sin guardar")
                .setMessage("Estás saliendo de la pantalla con cambios sin guardar. Desea hacerlo de todas maneras?")
                .setPositiveButton(R.string.dialog_exit, (dialog, which) -> PerfilUsuarioActivity.super.onBackPressed())
                .setNegativeButton(R.string.dialog_cancel, (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(ParquesApplication.getInstance().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void setNombreApellidoTxt(String nombre, String apellido) {
        txtNombreApellido.setText(String.format(Locale.getDefault(), "%s, %s", nombre, apellido));
    }

    private boolean datosDocumentoCorrectos(String docType) {
        UserFactoryEditText factoryEditText = new UserFactoryEditText();

        factoryEditText.setEtDocNum(etDocumento);
        factoryEditText.setDocType(docType);
        factoryEditText.setOrigin(UPDATE_USER_DOC_ORIGIN);

        return viewHelper.isValidData(factoryEditText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.perfil_usuario_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.eliminar_cuenta_menu:
                presenter.doDeleteCuenta(getUsuario().getId()); // TODO: 09/07/2018 Hacer una pantalla advirtiendo
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void navigateToLogin() {
        startActivity(new Intent(PerfilUsuarioActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void onCambiarNombYApe(String nombre, String apellido) {
        presenter.doUpdateName(getUsuario().getId(), nombre, apellido);
    }

    @Override
    public void onCambiarContrasenia(String oldPassword, String newPassword) {
        presenter.doUpdatePassword(new UsuarioPassword(getUsuario().getId(), newPassword, oldPassword));
    }
}

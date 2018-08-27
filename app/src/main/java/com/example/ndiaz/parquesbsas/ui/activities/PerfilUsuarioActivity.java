package com.example.ndiaz.parquesbsas.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.ndiaz.parquesbsas.helpers.login_google.LoginWithGoogle;
import com.example.ndiaz.parquesbsas.helpers.login_google.OnUserLoggedWithGoogleListener;
import com.example.ndiaz.parquesbsas.interactor.PerfilUsuarioInteractor;
import com.example.ndiaz.parquesbsas.listeners.OnCambiarContraseniaListener;
import com.example.ndiaz.parquesbsas.listeners.OnCambiarNombYApeListener;
import com.example.ndiaz.parquesbsas.model.Documento;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.model.UsuarioPassword;
import com.example.ndiaz.parquesbsas.presenter.PerfilUsuarioPresenter;
import com.example.ndiaz.parquesbsas.ui.dialogs.CambiarContraseniaDialogFragment;
import com.example.ndiaz.parquesbsas.ui.dialogs.CambiarNombYApeDialogFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.ndiaz.parquesbsas.edittextvalidator.usuario.UserFactoryEditText.UPDATE_USER_DOC_ORIGIN;

public class PerfilUsuarioActivity extends BaseActivity<PerfilUsuarioContract.Presenter>
        implements PerfilUsuarioContract.View, OnCambiarNombYApeListener, OnCambiarContraseniaListener, OnUserLoggedWithGoogleListener {

    private static final String TAG = PerfilUsuarioActivity.class.getSimpleName();
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
    @BindView(R.id.txtConnectWithGoogle)
    TextView txtConnectWithGoogle;

    @BindView(R.id.imgEditNombre)
    ImageButton imgEditNombre;
    @BindView(R.id.imgEditDoc)
    ImageButton imgEditDoc;
    @BindView(R.id.imgCommitEditDoc)
    ImageButton imgCommitEditDoc;
    @BindView(R.id.imgDismissEditDoc)
    ImageButton imgDismissEditDoc;

    private static final int REQUEST_CODE_GOOGLE_SIGN_IN = 1;
    private List<Documento> documentos;
    private TipoDocumentoHelper tipoDocumentoHelper;
    private ArrayAdapter<String> docTypesAdapter;
    private boolean docEditionInProgress;
    private ViewHelper viewHelper;
    private ParquesApplication parquesApplication;
    private LoginWithGoogle loginWithGoogle;

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
        presenter.doLogout(parquesApplication);
    }

    @OnClick(R.id.txtConnectWithGoogle)
    public void onConnectWithGoogleClick() {
        Intent signInIntent = ParquesApplication.getInstance().getGoogleSignInClient().getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_SIGN_IN);
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
        parquesApplication = ParquesApplication.getInstance();
        tipoDocumentoHelper = new TipoDocumentoHelper();
        viewHelper = new ViewHelper();
        loginWithGoogle = new LoginWithGoogle(getDefaultDefaultPreferencesRepository(), getNetworkServiceImp());
    }

    @Override
    protected PerfilUsuarioContract.Presenter createPresenter() {
        PerfilUsuarioContract.Interactor interactor = new PerfilUsuarioInteractor(getNetworkServiceImp(),
                getDefaultDefaultPreferencesRepository());

        return new PerfilUsuarioPresenter(this, interactor);
    }

    private void setupUI() {
        initializeToolbar();
        disableDocEdition();
        initializeTexts();
        initializeSpinner();
        txtConnectWithGoogle.setVisibility(parquesApplication.isLoggedWithGoogle() ?
                View.GONE : View.VISIBLE);
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
        Toast.makeText(parquesApplication.getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
                showDeleteCuentaDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDeleteCuentaDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.warning)
                .setIcon(R.drawable.ic_warning)
                .setMessage(R.string.eliminar_cuenta_texto)
                .setPositiveButton(R.string.dialog_delete, (dialog, which) -> {
                    presenter.doDeleteCuenta(getUsuario().getId());
                })
                .setNegativeButton(R.string.dialog_cancel, (dialog, which) -> dialog.dismiss())
                .show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_GOOGLE_SIGN_IN:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
                break;
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            loginWithGoogle.doVinculate(account, getUsuario().getId(), this);
        } catch (ApiException e) {
            if (e.getStatusCode() != CommonStatusCodes.ERROR) {
                showMessage("No se pudo iniciar sesión. Intentelo nuevamente más tarde");
            }
            Log.w(TAG, "Google signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    public void onLoginWithGoogleSuccess(Usuario usuario) {
        txtConnectWithGoogle.setVisibility(View.GONE);
        showMessage("Su cuenta ha sido vinculada correctamente");
    }

    @Override
    public void onLoginWithGoogleError(String message) {
        showMessage(message);
    }

    @Override
    protected void onStop() {
        loginWithGoogle.onStop();
        super.onStop();
    }
}

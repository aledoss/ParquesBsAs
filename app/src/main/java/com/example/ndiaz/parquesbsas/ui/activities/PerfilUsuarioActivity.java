package com.example.ndiaz.parquesbsas.ui.activities;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.PerfilUsuarioContract;
import com.example.ndiaz.parquesbsas.helpers.TipoDocumentoHelper;
import com.example.ndiaz.parquesbsas.interactor.PerfilUsuarioInteractor;
import com.example.ndiaz.parquesbsas.model.Documento;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.presenter.PerfilUsuarioPresenter;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class PerfilUsuarioActivity extends BaseActivity<PerfilUsuarioContract.Presenter>
        implements PerfilUsuarioContract.View {

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
        disableDocEdition();
        // TODO: 02/07/2018 Realizar validaciones
        String docType = spiDocType.getSelectedItem().toString();
        int idDoc = tipoDocumentoHelper.getDocIdFromText(docType, documentos);
        String numeroDoc = etDocumento.getText().toString();

        presenter.doUpdateDoc(new Documento(idDoc, docType, numeroDoc));
    }

    @OnClick(R.id.imgDismissEditDoc)
    public void onDismissEditDoc() {
        disableDocEdition();
        fillSpinner(new String[]{getUsuario().getTipoDoc()});
        etDocumento.setText(getUsuario().getNumeroDoc());
    }

    @OnClick(R.id.imgEditNombre)
    public void onEditNombreClick() {
        // TODO: 01/07/2018 Abrir custom dialog con edicion de nombre y apellido
    }

    @OnClick(R.id.txtCambiarContrasenia)
    public void onCambiarContraseniaClick() {

    }

    @OnClick(R.id.txtCerrarSesion)
    public void onCerrarSesionClick() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        tipoDocumentoHelper = new TipoDocumentoHelper();
        presenter.doGetDocTypes();
        setupUI();
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
    }

    private void disableDocEdition() {
        spiDocType.setEnabled(false);
        etDocumento.setEnabled(false);
        imgEditDoc.setVisibility(View.VISIBLE);
        imgCommitEditDoc.setVisibility(View.GONE);
        imgDismissEditDoc.setVisibility(View.GONE);
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
    public void updateName(Usuario usuario) {
        setNombreApellidoTxt(usuario.getNombre(), usuario.getApellido());
        getUsuario().setNombre(usuario.getNombre());
        getUsuario().setApellido(usuario.getApellido());
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
        super.onBackPressed();
        // TODO: 01/07/2018 Verificar que no esté nada en edicion 
    }

    private void setNombreApellidoTxt(String nombre, String apellido) {
        txtNombreApellido.setText(String.format(Locale.getDefault(), "%s, %s", nombre, apellido));
    }

    public void changePassword(Usuario usuario) {
        presenter.doUpdatePassword(usuario);
    }
}

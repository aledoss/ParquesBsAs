package com.example.ndiaz.parquesbsas.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.CreateUserContract;
import com.example.ndiaz.parquesbsas.edittextvalidator.usuario.UserFactoryEditText;
import com.example.ndiaz.parquesbsas.helpers.ViewHelper;
import com.example.ndiaz.parquesbsas.interactor.CreateUserInteractor;
import com.example.ndiaz.parquesbsas.model.Documento;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.presenter.CreateUserPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

public class CreateUserActivity extends BaseActivity<CreateUserContract.Presenter>
        implements CreateUserContract.View {

    @BindView(R.id.createAccountContainerLayout)
    LinearLayout lLContainer;
    @BindView(R.id.etNombreCrearCuenta)
    EditText etNombre;
    @BindView(R.id.etApellidoCrearCuenta)
    EditText etApellido;
    @BindView(R.id.etDNICrearCuenta)
    EditText etDocNumber;
    @BindView(R.id.etEmailCrearCuenta)
    EditText etEmail;
    @BindView(R.id.etPasswordCrearCuenta)
    EditText etPassword;
    @BindView(R.id.spiDocType)
    AppCompatSpinner spiDocType;

    private String nombre, apellido, docNumber, email, password;
    private Documento docType;
    private ViewHelper viewHelper;
    private List<Documento> documentos;

    @OnClick(R.id.btnCrear_Cuenta)
    public void onClickCreateUser() {
        getFieldsData();
        if (isValidData()) {
            Usuario usuario = new Usuario(nombre, apellido, docNumber, docType.getId(),
                    docType.getTipoDocumento(), email, password);
            presenter.doSaveUserData(usuario);
            presenter.doCreateUser(usuario);
        }
    }

    @OnTouch(R.id.etPasswordCrearCuenta)
    public boolean onTouchPasswordIcon(View v, MotionEvent event) {
        return viewHelper.tooglePasswordTextType(etPassword, event);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        setTransparentStatusBar();
        viewHelper = new ViewHelper();
        presenter.doGetDocTypes();
    }

    @Override
    protected CreateUserContract.Presenter createPresenter() {
        CreateUserInteractor interactor = new CreateUserInteractor(
                getDefaultDefaultPreferencesRepository(), networkServiceImp);
        return new CreateUserPresenter(this, interactor);
    }

    private boolean isValidData() {
        UserFactoryEditText factoryEditText = new UserFactoryEditText(etNombre, etApellido, etEmail,
                etPassword, etDocNumber, docType.getTipoDocumento());
        return viewHelper.isValidData(factoryEditText);
    }

    private void getFieldsData() {
        nombre = etNombre.getText().toString();
        apellido = etApellido.getText().toString();
        docNumber = etDocNumber.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        String docTypeSelected = spiDocType.getSelectedItem().toString();
        docType = new Documento(getDocTypeId(docTypeSelected), docTypeSelected);
    }

    private int getDocTypeId(String docTypeSelected) {
        int docTypeId = 1;
        for (Documento documento : documentos) {
            if (documento.getTipoDocumento().equalsIgnoreCase(docTypeSelected)) {
                docTypeId = documento.getId();
            }
        }
        return docTypeId;
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(CreateUserActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showCreateUserError(String message) {
        showMessage(lLContainer, message);
    }

    @Override
    public void fillSpinner(String[] docTypes) {
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.spinner_doctype_item_white,
                docTypes);
        spiDocType.setAdapter(adapter);
    }

    @Override
    public void setDocTypes(List<Documento> documentos) {
        this.documentos = documentos;
    }
}

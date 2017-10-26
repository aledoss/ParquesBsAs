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
import com.example.ndiaz.parquesbsas.database.DBHelper;
import com.example.ndiaz.parquesbsas.edittextvalidator.FactoryEditText;
import com.example.ndiaz.parquesbsas.helpers.LoginCreateViewHelper;
import com.example.ndiaz.parquesbsas.interactor.CreateUserInteractor;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.presenter.CreateUserPresenter;

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
    private String[] docTypes;
    private LoginCreateViewHelper loginCreateViewHelper;

    @OnClick(R.id.btnCrear_Cuenta)
    public void onClickCreateUser() {
        getFieldsData();
        if (isValidData()) {
            presenter.doCreateUser(new Usuario(nombre, apellido, email, password, docNumber,
                    "DNI"));
        }
        navigateToHome();
    }

    @OnTouch(R.id.etPasswordCrearCuenta)
    public boolean onTouchPasswordIcon(View v, MotionEvent event) {
        return loginCreateViewHelper.tooglePasswordTextType(etPassword, event);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        setTransparentStatusBar();
        loginCreateViewHelper = new LoginCreateViewHelper();
        presenter.doGetDocTypes();
    }

    @Override
    protected CreateUserContract.Presenter createPresenter() {
        CreateUserInteractor interactor = new CreateUserInteractor(
                defaultPreferencesRepository, networkServiceImp);
        // TODO: 16/10/2017 Agregar el new del Presenter
        return new CreateUserPresenter(this, interactor);
    }

    private boolean isValidData() {
        FactoryEditText factoryEditText = new FactoryEditText(etNombre, etApellido, etEmail,
                etPassword, etDocNumber, "DNI");// TODO: 17/10/2017 Spinner del doctype
        return loginCreateViewHelper.isValidData(factoryEditText);
    }

    //Funcionalidad de autologin + acceder al home
    private void accederHome(Usuario usuario) {
        /*SharedPreferences sharedPreferences = getSharedPreferences(LOGINPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAILLOGINSAVED, email);
        editor.putString(PASSWORDLOGINSAVED, password);
        editor.commit();
        Intent intent = new Intent(CreateUserActivity.this, MainHome.class);
        intent.putExtra(CREARCUENTAUSUARIO, (Serializable) usuario);
        startActivity(intent);
        finish();*/
    }

    //Esto vendría del servidor
    private boolean cuentaDuplicada() {
        boolean cuentaDuplicada;
        DBHelper db = new DBHelper(this);
        Usuario usuario = db.getUsuario(email, password);
        if (usuario == null) {
            db.close();
            cuentaDuplicada = false;
        } else {
            db.close();
            cuentaDuplicada = true;
        }
        return cuentaDuplicada;
    }

    // Lo maneja el servidor
    private Usuario crearCuenta() {
        DBHelper db = new DBHelper(this);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setNumeroDoc(docNumber);
        usuario.setEmail(email);
        usuario.setPassword(password);

        db.insertarUsuario(usuario);
        db.close();

        return usuario;
    }

    private void getFieldsData() {
        nombre = etNombre.getText().toString();
        apellido = etApellido.getText().toString();
        docNumber = etDocNumber.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(CreateUserActivity.this, MainHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showCreateUserError(String message) {
        showMessage(lLContainer, message);
    }

    @Override
    public void fillSpinner(String[] docTypes) {
        this.docTypes = docTypes;
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.spinner_doctype_item,
                docTypes);
        spiDocType.setAdapter(adapter);
    }
}

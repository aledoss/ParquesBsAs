package com.example.ndiaz.parquesbsas.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.LoginContract;
import com.example.ndiaz.parquesbsas.edittextvalidator.usuario.UserFactoryEditText;
import com.example.ndiaz.parquesbsas.helpers.ViewHelper;
import com.example.ndiaz.parquesbsas.interactor.LoginInteractor;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.presenter.LoginPresenter;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

import static com.example.ndiaz.parquesbsas.constants.LoginConstants.EMAILLOGINSAVED;
import static com.example.ndiaz.parquesbsas.constants.LoginConstants.INICIARSESIONUSUARIO;
import static com.example.ndiaz.parquesbsas.constants.LoginConstants.LOGINPREFERENCES;
import static com.example.ndiaz.parquesbsas.constants.LoginConstants.PASSWORDLOGINSAVED;

public class LoginActivity extends BaseActivity<LoginContract.Presenter>
        implements LoginContract.View {

    @BindView(R.id.etEmailLogin)
    EditText etEmail;
    @BindView(R.id.etPasswordLogin)
    EditText etPassword;
    @BindView(R.id.loginContainerLayout)
    LinearLayout lLContainer;
    private String email, password;
    private boolean recordarDatosLogin;
    private ViewHelper loginCreateViewHelper;

    @OnClick(R.id.btnIniciar_Sesion)
    public void onClickIniciarSesion() {
        getLoginData();
        if (isValidData()) {
            presenter.doLogin(new Usuario(email, password));
        }
    }

    @OnClick(R.id.btnCrear_Cuenta_Login)
    public void onClickCrearCuenta() {
        startActivity(new Intent(LoginActivity.this, CreateUserActivity.class));
    }

    @OnClick(R.id.btnRecuperarContraseña)
    public void onClickRecuPass() {
        showMessage(lLContainer, getString(R.string.WorkInProgress));
    }

    @OnTouch(R.id.etPasswordLogin)
    public boolean onTouchPasswordIcon(View v, MotionEvent event) {
        return loginCreateViewHelper.tooglePasswordTextType(etPassword, event);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //verificarLogin();   // TODO: 14/10/2017 Funcionalidad de autologin.
        setContentView(R.layout.activity_login);
        setTransparentStatusBar();
        loginCreateViewHelper = new ViewHelper();
    }

    @Override
    protected LoginContract.Presenter createPresenter() {
        LoginContract.Interactor loginInteractor = new LoginInteractor(
                getDefaultPreferencesRepository(), networkServiceImp
        );

        return new LoginPresenter(this, loginInteractor);
    }

    // TODO: 14/10/2017 Funcionalidad de autologin.
    private void verificarLogin() {
        try {
            //pedirlas al presenter e interactor. Si da OK, hacer un loginView.login(datos)
            recordarDatosLogin = obtenerDatosSharedPreferencesSettings();
            if (recordarDatosLogin) {
                obtenerDatosLoginSharedPreferences();
                Usuario usuario = obtenerUsuario();
                if (usuario != null) {
                    iniciarSesion(usuario, false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: 14/10/2017 Funcionalidad de autologin.
    private void obtenerDatosLoginSharedPreferences() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(LOGINPREFERENCES, Context.MODE_PRIVATE);
            email = sharedPreferences.getString(EMAILLOGINSAVED, "");
            password = sharedPreferences.getString(PASSWORDLOGINSAVED, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: 14/10/2017 Funcionalidad de autologin.
    private boolean obtenerDatosSharedPreferencesSettings() {
        //obtengo datos del settings
        /*SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        recordarDatosLogin = sharedPrefs.getBoolean(SETTINGS_CHECBOX_INICIO_SESION_AUTO, true);
        Log.d("SETTINGS", "Recordar: " + recordarDatosLogin);
        return recordarDatosLogin;*/
        return false;
    }

    private Usuario obtenerUsuario() {
        /*
        DBHelper db = new DBHelper(this);
        Usuario usuario = db.getUsuario(email, password);
        db.close();
        if (usuario != null) {
            return usuario;
        }*/
        return null;
    }

    private boolean isValidData() {
        return loginCreateViewHelper.isValidData(new UserFactoryEditText(etEmail, etPassword));
    }

    // TODO: 14/10/2017 Se realiza desde el presenter
    private void iniciarSesion(Usuario usuario, boolean guardarDatos) {
        if (guardarDatos) {
            try {
                SharedPreferences sharedPreferences = getSharedPreferences(LOGINPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(EMAILLOGINSAVED, email);
                editor.putString(PASSWORDLOGINSAVED, password);
                editor.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra(INICIARSESIONUSUARIO, (Serializable) usuario);
        startActivity(intent);
        finish();
    }

    private void getLoginData() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void showLoginError(String message) {
        showMessage(lLContainer, message);
    }

}

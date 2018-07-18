package com.example.ndiaz.parquesbsas.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.LoginContract;
import com.example.ndiaz.parquesbsas.edittextvalidator.usuario.UserFactoryEditText;
import com.example.ndiaz.parquesbsas.helpers.ViewHelper;
import com.example.ndiaz.parquesbsas.interactor.LoginInteractor;
import com.example.ndiaz.parquesbsas.listeners.OnRecuperarContraseniaListener;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.presenter.LoginPresenter;
import com.example.ndiaz.parquesbsas.ui.dialogs.RecuperarContraseniaDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

public class LoginActivity extends BaseActivity<LoginContract.Presenter>
        implements LoginContract.View, OnRecuperarContraseniaListener {

    @BindView(R.id.etEmailLogin)
    EditText etEmail;
    @BindView(R.id.etPasswordLogin)
    EditText etPassword;
    @BindView(R.id.loginContainerLayout)
    ConstraintLayout container;

    private String email, password;
    private ViewHelper loginCreateViewHelper;

    @OnClick(R.id.btnIniciar_Sesion)
    public void onClickIniciarSesion() {
        getLoginData();
        if (isValidData()) {
            presenter.doLogin(new Usuario(email, password), true);
        }
    }

    @OnClick(R.id.btnCrear_Cuenta_Login)
    public void onClickCrearCuenta() {
        startActivity(new Intent(LoginActivity.this, CreateUserActivity.class));
    }

    @OnClick(R.id.txtOmitir)
    public void onClickOmitir() {
        navigateToHome();
    }

    @OnClick(R.id.btnRecuperarContraseña)
    public void onClickRecuPass() {
        RecuperarContraseniaDialogFragment fragment = RecuperarContraseniaDialogFragment.newInstance(this);
        fragment.show(getSupportFragmentManager(), RecuperarContraseniaDialogFragment.class.getSimpleName());
    }

    @OnTouch(R.id.etPasswordLogin)
    public boolean onTouchPasswordIcon(View v, MotionEvent event) {
        return loginCreateViewHelper.tooglePasswordTextType(etPassword, event);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter.doGetIsAutoLoginEnabled();
        setTransparentStatusBar();
        loginCreateViewHelper = new ViewHelper();

//        presenter.doLogin(new Usuario("juan.perez@hotmail.com", "Asd1234$"), false);  todo login automatico, sacar.
    }

    @Override
    protected LoginContract.Presenter createPresenter() {
        LoginContract.Interactor loginInteractor = new LoginInteractor(
                getDefaultDefaultPreferencesRepository(), networkServiceImp
        );

        return new LoginPresenter(this, loginInteractor);
    }

    private boolean isValidData() {
        return loginCreateViewHelper.isValidData(new UserFactoryEditText(etEmail, etPassword));
    }

    private void getLoginData() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
    }

    @Override
    public void onAutoLoginEnabled(Boolean isAutoLoginEnabled) {
        if (isAutoLoginEnabled) {
            presenter.doAutoLogin();
        }
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void showLoginError(String message) {
        showMessage(container, message);
    }

    @Override
    public void onRecuperarContrasenia(String email) {
        presenter.doRecuperarContrasenia(email);
    }

    @Override
    public void showMessage(String message){
        showMessage(container, message);
    }
}

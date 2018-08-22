package com.example.ndiaz.parquesbsas.presenter;

import android.util.Log;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.LoginContract;
import com.example.ndiaz.parquesbsas.model.Usuario;

public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();
    private LoginContract.View view;
    private LoginContract.Interactor interactor;

    public LoginPresenter(LoginContract.View view, LoginContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void doLogin(Usuario usuario, boolean updateUserData) {
        view.showProgressDialog();
        interactor.login(usuario, new BaseCallback<Usuario>() {
            @Override
            public void onSuccess(Usuario usuarioLogueado) {
                ParquesApplication.getInstance().setUser(usuarioLogueado);
                if (updateUserData) {
                    interactor.updateUserData(usuarioLogueado);
                }
                view.navigateToHome();
                view.hideKeyboard();
                view.hideProgressDialog();
            }

            @Override
            public void onError(String message) {
                view.hideKeyboard();
                view.hideProgressDialog();
                view.showLoginError(message);
                Log.e(TAG, "doLogin onError: " + message);
            }
        });
    }

    @Override
    public void doAutoLogin() {
        interactor.getLoginData(usuario -> {
            if (usuario.getEmail() != null && !usuario.getEmail().isEmpty())
                doLogin(usuario, false);
        });
    }

    @Override
    public void doGetIsAutoLoginEnabled() {
        interactor.isAutoLoginEnabled(autoLoginEnabled -> view.onAutoLoginEnabled(autoLoginEnabled));
    }

    @Override
    public void doRecuperarContrasenia(String email) {
        view.showProgressDialog();
        interactor.recuperarContrasenia(email, new BaseCallback<String>() {
            @Override
            public void onSuccess(String message) {
                view.hideProgressDialog();
                view.showMessage(message);
            }

            @Override
            public void onError(String message) {
                view.hideProgressDialog();
                view.showMessage(message);
            }
        });
    }

    @Override
    public void doLoginWithGoogle(Usuario usuario) {
        view.showProgressDialog();
        interactor.loginWithGoogle(usuario, new BaseCallback<Usuario>() {
            @Override
            public void onSuccess(Usuario usuarioLogueado) {
                ParquesApplication.getInstance().setUser(usuarioLogueado);
                ParquesApplication.getInstance().setLoggedWithGoogle(true);
                view.navigateToHome();
                view.hideProgressDialog();
            }

            @Override
            public void onError(String message) {
                view.hideProgressDialog();
                view.showMessage(message);
            }
        });
    }
}

package com.example.ndiaz.parquesbsas.presenter;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.LoginContract;
import com.example.ndiaz.parquesbsas.model.Usuario;

public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();
    private LoginContract.View loginView;
    private LoginContract.Interactor loginInteractor;

    public LoginPresenter(LoginContract.View loginView, LoginContract.Interactor loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void doLogin(Usuario usuario) {
        loginInteractor.login(usuario, new BaseCallback() {
            @Override
            public void onSuccess(Object usuario) {
                loginView.navigateToHome();
            }

            @Override
            public void onError(String message) {
                Log.d(TAG, "onError: " + message);
            }
        });
    }
}

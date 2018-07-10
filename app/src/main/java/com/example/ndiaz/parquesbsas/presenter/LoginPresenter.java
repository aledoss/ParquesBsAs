package com.example.ndiaz.parquesbsas.presenter;

import android.util.Log;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.LoginContract;
import com.example.ndiaz.parquesbsas.model.Documento;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Usuario;

import java.util.List;

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
        loginInteractor.login(usuario, new BaseCallback<NetworkResponse<List<Documento>>>() {
            @Override
            public void onSuccess(NetworkResponse<List<Documento>> response) {
                if(response.getResponse() != null){
                    loginView.navigateToHome();
                    ParquesApplication.getInstance().setUser((Usuario) response.getResponse());
                }else{
                    loginView.hideKeyboard();
                    loginView.showLoginError(response.getMessage());
                }
            }

            @Override
            public void onError(String message) {
                loginView.showLoginError(message);
                Log.e(TAG, "doLogin onError: " + message);
            }
        });
    }
}

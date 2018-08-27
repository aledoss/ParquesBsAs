package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.SplashScreenContract;
import com.example.ndiaz.parquesbsas.model.Usuario;

public class SplashScreenPresenter implements SplashScreenContract.Presenter {

    private static final String TAG = SplashScreenPresenter.class.getSimpleName();
    private SplashScreenContract.View view;
    private SplashScreenContract.Interactor interactor;

    public SplashScreenPresenter(SplashScreenContract.View view, SplashScreenContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void doLogin(Usuario usuario) {
        interactor.login(usuario, new BaseCallback<Usuario>() {
            @Override
            public void onSuccess(Usuario usuarioLogueado) {
                ParquesApplication.getInstance().setUser(usuarioLogueado);
                view.navigateToHome();
            }

            @Override
            public void onError(String message) {
                view.navigateToLogin();
            }
        });
    }

    @Override
    public void doAutoLogin() {
        interactor.getLoginData(usuario -> {
            if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()
                    && usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                doLogin(usuario);
            } else {
                view.navigateToLogin();
            }
        });
    }

    @Override
    public void doLoginWithGoogle(Usuario usuario) {
        interactor.loginWithGoogle(usuario, new BaseCallback<Usuario>() {
            @Override
            public void onSuccess(Usuario usuarioLogueado) {
                ParquesApplication.getInstance().setUser(usuarioLogueado);
                ParquesApplication.getInstance().setLoggedWithGoogle(true);
                view.navigateToHome();
            }

            @Override
            public void onError(String message) {
                view.navigateToLogin();
            }
        });
    }

    @Override
    public void onStop() {
        interactor.unsubscribeAll();
    }
}

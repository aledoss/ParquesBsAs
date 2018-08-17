package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.SingleCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Usuario;

public interface SplashScreenContract {

    interface View extends BaseView {
        void navigateToHome();

        void navigateToLogin();
    }

    interface Presenter extends BasePresenter {
        void doLogin(Usuario usuario);

        void doAutoLogin();

        void onStop();

        void doLoginWithGoogle(Usuario usuario);
    }

    interface Interactor extends BaseInteractor {
        void login(Usuario usuario, BaseCallback<Usuario> callback);

        void getLoginData(SingleCallback<Usuario> usuario);

        void loginWithGoogle(Usuario usuario, BaseCallback<Usuario> callback);
    }
}

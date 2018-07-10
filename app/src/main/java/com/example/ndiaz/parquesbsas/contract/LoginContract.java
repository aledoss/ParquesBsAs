package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Usuario;

public interface LoginContract {

    interface View extends BaseView {
        void navigateToHome();

        void showLoginError(String message);

        void hideKeyboard();
    }

    interface Presenter extends BasePresenter {
        void doLogin(Usuario usuario);
    }

    interface Interactor extends BaseInteractor {
        void login(Usuario usuario, BaseCallback callback);
    }

}

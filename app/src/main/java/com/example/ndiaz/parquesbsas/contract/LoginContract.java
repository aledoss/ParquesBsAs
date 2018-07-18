package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.SingleCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Usuario;

public interface LoginContract {

    interface View extends BaseView {
        void showMessage(String message);

        void navigateToHome();

        void showLoginError(String message);

        void hideKeyboard();

        void onAutoLoginEnabled(Boolean autoLoginEnabled);
    }

    interface Presenter extends BasePresenter {
        void doLogin(Usuario usuario, boolean updateUserData);

        void doGetIsAutoLoginEnabled();

        void doAutoLogin();

        void doRecuperarContrasenia(String email);
    }

    interface Interactor extends BaseInteractor {
        void login(Usuario usuario, BaseCallback<Usuario> callback);

        void isAutoLoginEnabled(SingleCallback<Boolean> isAutologinEnabled);

        void getLoginData(SingleCallback<Usuario> usuario);

        void updateUserData(Usuario usuario);

        void recuperarContrasenia(String email, BaseCallback<String> callback);
    }

}

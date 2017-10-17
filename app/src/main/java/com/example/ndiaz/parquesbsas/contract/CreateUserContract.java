package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Usuario;

public interface CreateUserContract {

    interface View extends BaseView {
        void navigateToHome();

        void showCreateUserError(String message);
    }

    interface Presenter extends BasePresenter {
        void doCreateUser(Usuario usuario);
    }

    interface Interactor extends BaseInteractor {
        void createUser(Usuario usuario, BaseCallback<Boolean> callback);
    }

}

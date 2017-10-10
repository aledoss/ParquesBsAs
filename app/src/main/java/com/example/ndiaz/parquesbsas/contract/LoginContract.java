package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;

public interface LoginContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {

    }

    interface Interactor extends BaseInteractor {
        void login(String email, String password, BaseCallback callback);
    }

}

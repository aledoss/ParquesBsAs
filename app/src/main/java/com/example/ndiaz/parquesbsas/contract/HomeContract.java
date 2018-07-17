package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Parque;

import java.util.List;

public interface HomeContract {

    interface View extends BaseView {
        void loadParques(List<Parque> parques);
        void navigateToParque(Parque parque);
        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void doGetParques();
        void doGetParqueFromNetw(int parqueId);
    }

    interface Interactor extends BaseInteractor {
        void getParques(BaseCallback<List<Parque>> callback);
        void getParqueNetwork(int parqueId, BaseCallback<Parque> callback);
    }

}

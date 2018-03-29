package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.PuntoVerde;

import java.util.List;

public interface ListaPuntosVerdesContract {

    interface View extends BaseView {
        void showPuntosVerdes(List<PuntoVerde> puntosVerdes);

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void doGetPuntosVerdes(int idParque);
    }

    interface Interactor extends BaseInteractor {
        void getPuntosVerdes(int idParque, BaseCallback<List<PuntoVerde>> callback);
    }

}

package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Actividad;

import java.util.List;

public interface ListaActividadesContract {

    interface View extends BaseView {
        void showActividades(List<Actividad> actividades);

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void doGetActividades(int idParque);
    }

    interface Interactor extends BaseInteractor {
        void getActividades(int idParque, BaseCallback<List<Actividad>> callback);
    }

}

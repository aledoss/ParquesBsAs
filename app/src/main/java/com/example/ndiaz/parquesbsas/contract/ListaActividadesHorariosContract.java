package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Actividad;

import java.util.List;

public interface ListaActividadesHorariosContract {

    interface View extends BaseView {
        void showHorarios(List<Actividad> actividades);

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void doGetHorarios(int idParque, int idActividad);
    }

    interface Interactor extends BaseInteractor {
        void getHorarios(int idParque, int idActividad, BaseCallback<List<Actividad>> callback);
    }

}

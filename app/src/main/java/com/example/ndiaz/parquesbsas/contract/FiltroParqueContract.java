package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.model.Feria;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.ParqueFilter;

import java.util.List;

public interface FiltroParqueContract {

    interface View extends BaseView {
        void showMessage(String message);

        void showActividades(List<Actividad> actividades);

        void showFerias(List<Feria> ferias);

        void showListParquesActivity(List<Parque> parques);
    }

    interface Presenter extends BasePresenter {
        void doGetActividades();

        void doGetFerias();

        void doFilterParques(ParqueFilter filter);
    }

    interface Interactor extends BaseInteractor {
        void getActividadesToFilter(BaseCallback<List<Actividad>> callback);

        void getFeriasToFilter(BaseCallback<List<Feria>> callback);

        void filterParques(ParqueFilter filter, BaseCallback<List<Parque>> callback);
    }

}

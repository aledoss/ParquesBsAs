package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.EmptyCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.model.Feria;
import com.example.ndiaz.parquesbsas.model.ParqueFilter;

import java.util.List;

public interface FiltroParqueContract {

    interface View extends BaseView {
        void showMessage(String message);

        void showActividades(List<Actividad> actividades);

        void showFerias(List<Feria> ferias);
    }

    interface Presenter extends BasePresenter {
        void doGetActividades();

        void doGetFerias();

        void doFilter(ParqueFilter filter);
    }

    interface Interactor extends BaseInteractor {
        void getActividades(BaseCallback<List<Actividad>> callback);

        void getFerias(BaseCallback<List<Feria>> callback);

        void filter(ParqueFilter filter, EmptyCallback callback);
    }

}

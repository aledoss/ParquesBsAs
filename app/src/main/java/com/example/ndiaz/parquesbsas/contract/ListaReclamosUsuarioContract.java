package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.EmptyCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.ReclamoFecha;

import java.util.List;

public interface ListaReclamosUsuarioContract {

    interface View extends BaseView {
        void showReclamosConFechas(List<ReclamoFecha> reclamosFechas);

        void showMessage(String message);

        void showEmptyContainer();

        void refreshReclamos(List<ReclamoFecha> reclamosFechas);

        void hideSwipeRefresh();

        void callGetReclamosConFechas(boolean refreshData);
    }

    interface Presenter extends BasePresenter {
        void doGetReclamosConFechas(int idUsuario, boolean refreshData);

        void doDeleteReclamo(int idReclamo);
    }

    interface Interactor extends BaseInteractor {
        void getReclamosFecha(int idUsuario, BaseCallback<List<ReclamoFecha>> callback);

        void deleteReclamo(int idReclamo, EmptyCallback emptyCallback);
    }

}

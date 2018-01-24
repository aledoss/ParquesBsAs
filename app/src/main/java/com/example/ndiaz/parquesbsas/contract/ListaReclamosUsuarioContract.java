package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Reclamo;

import java.util.List;

public interface ListaReclamosUsuarioContract {

    interface View extends BaseView {
        void showReclamos(List<Reclamo> reclamos);

        void showMessage(String message);

        void showEmptyContainer();

        void refreshReclamos(List<Reclamo> reclamos);
    }

    interface Presenter extends BasePresenter {
        void doGetReclamos(int idUsuario, boolean refreshData);
    }

    interface Interactor extends BaseInteractor {
        void getReclamos(int idUsuario, BaseCallback<List<Reclamo>> callback);
    }

}

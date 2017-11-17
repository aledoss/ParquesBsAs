package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Reclamo;

import java.util.List;

public interface AgregarReclamoContract {

    interface View extends BaseView {
        void fillReclamos(List<Reclamo> reclamos);
        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void doGetReclamos();
    }

    interface Interactor extends BaseInteractor {
        void getReclamos(BaseCallback<List<Reclamo>> callback);
    }

}

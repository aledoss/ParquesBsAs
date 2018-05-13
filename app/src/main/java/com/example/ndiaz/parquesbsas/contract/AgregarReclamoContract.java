package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Reclamo;

import java.util.List;

public interface AgregarReclamoContract {

    interface View extends BaseView {
        void setReclamos(List<Reclamo> reclamos);

        void showMessage(String message);

        void navegarAListaReclamos(String value);

        void showRetryUploadingPhoto(Reclamo reclamo);
    }

    interface Presenter extends BasePresenter {
        void doGetReclamos();

        void doInsertReclamo(Reclamo reclamo);

        void doInsertReclamoWithPhoto(Reclamo reclamo);
    }

    interface Interactor extends BaseInteractor {
        void getReclamos(BaseCallback<List<Reclamo>> callback);

        void insertReclamo(Reclamo reclamo, BaseCallback<String> callback);

        void uploadPhoto(Reclamo reclamo, BaseCallback<String> baseCallback);
    }

}

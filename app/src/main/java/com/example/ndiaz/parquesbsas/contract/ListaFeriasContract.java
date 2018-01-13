package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Feria;

import java.util.List;

public interface ListaFeriasContract {

    interface View extends BaseView {
        void showFerias(List<Feria> ferias);

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void doGetFerias(int idParque);
    }

    interface Interactor extends BaseInteractor {
        void getFerias(int idParque, BaseCallback<List<Feria>> callback);
    }

}

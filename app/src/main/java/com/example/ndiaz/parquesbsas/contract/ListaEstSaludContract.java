package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.EstacionSaludable;

import java.util.List;

public interface ListaEstSaludContract {

    interface View extends BaseView {
        void showEstSalud(List<EstacionSaludable> estSaludables);

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void doGetEstSalud(int idParque);
    }

    interface Interactor extends BaseInteractor {
        void getEstSalud(int idParque, BaseCallback<List<EstacionSaludable>> callback);
    }

}

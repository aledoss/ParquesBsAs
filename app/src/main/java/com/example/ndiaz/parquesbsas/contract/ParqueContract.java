package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.ParqueComponentes;

import java.util.List;

public interface ParqueContract {

    interface View extends BaseView {
        void showParqueComponents(List<ParqueComponentes> componentes);

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void doGetParqueComponents();
    }

    interface Interactor extends BaseInteractor {
        void getParqueComponents(BaseCallback<List<ParqueComponentes>> callback);
    }

}

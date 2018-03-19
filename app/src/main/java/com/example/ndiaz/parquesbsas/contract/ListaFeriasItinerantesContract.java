package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.FeriaItinerante;

import java.util.List;

public interface ListaFeriasItinerantesContract {

    interface View extends BaseView {
        void showFeriasItinerantes(List<FeriaItinerante> ferias);

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void doGetFeriasItinerantes(int idParque);
    }

    interface Interactor extends BaseInteractor {
        void getFeriasItinerantes(int idParque, BaseCallback<List<FeriaItinerante>> callback);
    }

}

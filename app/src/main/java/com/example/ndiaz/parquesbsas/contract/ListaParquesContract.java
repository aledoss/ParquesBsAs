package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Parque;

import java.util.List;

public interface ListaParquesContract {

    interface View extends BaseView {
        void showEmptyAdapter();

        void showParques(List<Parque> parques, boolean refreshData);

        void showMessage(String message);

        void navigateToParqueDetails(Parque parque);
    }

    interface Presenter extends BasePresenter {
        void doGetParques();

        void doGetParquesFiltered(List<Parque> itemList, String newText);

        void doGetParque(Integer idParque);
    }

    interface Interactor extends BaseInteractor {
        void getParques(BaseCallback<List<Parque>> callback);

        void getParquesFiltered(List<Parque> itemList, String newText, BaseCallback<List<Parque>> callback);

        void getParque(Integer idParque, BaseCallback<Parque> callback);
    }

}

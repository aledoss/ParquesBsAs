package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaFeriasItinerantesContract;
import com.example.ndiaz.parquesbsas.model.FeriaItinerante;

import java.lang.ref.WeakReference;
import java.util.List;

public class ListaFeriasItinerantesPresenter extends BasePresenterImp
        implements ListaFeriasItinerantesContract.Presenter {

    private WeakReference<ListaFeriasItinerantesContract.View> view;
    private ListaFeriasItinerantesContract.Interactor interactor;

    public ListaFeriasItinerantesPresenter(ListaFeriasItinerantesContract.View view,
                                           ListaFeriasItinerantesContract.Interactor interactor) {
        this.view = new WeakReference<>(view);
        this.interactor = interactor;
    }


    @Override
    public void doGetFeriasItinerantes(int idParque) {
        interactor.getFeriasItinerantes(idParque, new BaseCallback<List<FeriaItinerante>>() {
            @Override
            public void onSuccess(List<FeriaItinerante> ferias) {
                view.get().showFeriasItinerantes(ferias);
            }

            @Override
            public void onError(String message) {
                view.get().showMessage(message);
            }
        });
    }
}

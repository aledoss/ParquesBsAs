package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaParquesContract;
import com.example.ndiaz.parquesbsas.model.Parque;

import java.lang.ref.WeakReference;
import java.util.List;

public class ListaParquesPresenter extends BasePresenterImp implements ListaParquesContract.Presenter {

    private WeakReference<ListaParquesContract.View> listaParquesView;
    private ListaParquesContract.Interactor listaParquesInteractor;

    public ListaParquesPresenter(ListaParquesContract.View listaParquesView, ListaParquesContract.Interactor listaParquesInteractor) {
        this.listaParquesView = new WeakReference<>(listaParquesView);
        this.listaParquesInteractor = listaParquesInteractor;
    }

    public void doGetParques() {
        listaParquesInteractor.getParques(new BaseCallback<List<Parque>>() {
            @Override
            public void onSuccess(List<Parque> parques) {
                listaParquesView.get().showParques(parques);
            }

            @Override
            public void onError(String message) {
                listaParquesView.get().showMessage(message);
            }
        });
    }
}

package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaReclamosParqueContract;
import com.example.ndiaz.parquesbsas.model.Reclamo;

import java.lang.ref.WeakReference;
import java.util.List;

public class ListaReclamosParquePresenter extends BasePresenterImp implements
        ListaReclamosParqueContract.Presenter {

    private WeakReference<ListaReclamosParqueContract.View> reclamosParqueView;
    private ListaReclamosParqueContract.Interactor reclamosParqueInteractor;

    public ListaReclamosParquePresenter(ListaReclamosParqueContract.View reclamosParqueView,
                                        ListaReclamosParqueContract.Interactor reclamosParqueInteractor) {
        this.reclamosParqueView = new WeakReference<>(reclamosParqueView);
        this.reclamosParqueInteractor = reclamosParqueInteractor;
    }

    @Override
    public void doGetReclamos(int idParque, final boolean refreshData) {
        reclamosParqueInteractor.getReclamos(idParque, new BaseCallback<List<Reclamo>>() {
            @Override
            public void onSuccess(List<Reclamo> reclamos) {
                if (!refreshData) {
                    reclamosParqueView.get().showReclamos(reclamos);
                } else {
                    reclamosParqueView.get().refreshReclamos(reclamos);
                }
            }

            @Override
            public void onError(String message) {
                reclamosParqueView.get().showEmptyContainer();
            }
        });
    }
}

package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaPuntosVerdesContract;
import com.example.ndiaz.parquesbsas.model.PuntoVerde;

import java.lang.ref.WeakReference;
import java.util.List;

public class ListaPuntosVerdesPresenter extends BasePresenterImp
        implements ListaPuntosVerdesContract.Presenter {

    private WeakReference<ListaPuntosVerdesContract.View> view;
    private ListaPuntosVerdesContract.Interactor interactor;

    public ListaPuntosVerdesPresenter(ListaPuntosVerdesContract.View view,
                                      ListaPuntosVerdesContract.Interactor interactor) {
        this.view = new WeakReference<>(view);
        this.interactor = interactor;
    }


    @Override
    public void doGetPuntosVerdes(int idParque) {
        interactor.getPuntosVerdes(idParque, new BaseCallback<List<PuntoVerde>>() {
            @Override
            public void onSuccess(List<PuntoVerde> puntosVerdes) {
                view.get().showPuntosVerdes(puntosVerdes);
            }

            @Override
            public void onError(String message) {
                view.get().showMessage(message);
            }
        });
    }
}

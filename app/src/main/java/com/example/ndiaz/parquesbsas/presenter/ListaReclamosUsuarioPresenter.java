package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.EmptyCallback;
import com.example.ndiaz.parquesbsas.contract.ListaReclamosUsuarioContract;
import com.example.ndiaz.parquesbsas.model.ReclamoFecha;

import java.lang.ref.WeakReference;
import java.util.List;

public class ListaReclamosUsuarioPresenter extends BasePresenterImp implements
        ListaReclamosUsuarioContract.Presenter {

    private WeakReference<ListaReclamosUsuarioContract.View> view;
    private ListaReclamosUsuarioContract.Interactor interactor;

    public ListaReclamosUsuarioPresenter(ListaReclamosUsuarioContract.View view,
                                         ListaReclamosUsuarioContract.Interactor interactor) {
        this.view = new WeakReference<>(view);
        this.interactor = interactor;
    }

    @Override
    public void doGetReclamosConFechas(int idParque, final boolean refreshData) {
        interactor.getReclamosFecha(idParque, new BaseCallback<List<ReclamoFecha>>() {
            @Override
            public void onSuccess(List<ReclamoFecha> reclamosFechas) {
                if (reclamosFechas.isEmpty()) {
                    view.get().showEmptyContainer();
                } else {
                    if (!refreshData) {
                        view.get().showReclamosConFechas(reclamosFechas);
                    } else {
                        view.get().refreshReclamos(reclamosFechas);
                        view.get().hideSwipeRefresh();
                    }
                }
            }

            @Override
            public void onError(String message) {
                view.get().showEmptyContainer();
            }
        });
    }

    @Override
    public void doDeleteReclamo(int idReclamo) {
        view.get().showProgressDialog();
        interactor.deleteReclamo(idReclamo, new EmptyCallback() {
            @Override
            public void onSuccess() {
                view.get().hideProgressDialog();
                view.get().callGetReclamosConFechas(true);
            }

            @Override
            public void onError(String message) {
                view.get().hideProgressDialog();
                view.get().showMessage(message);
            }
        });
    }
}

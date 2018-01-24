package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaReclamosUsuarioContract;
import com.example.ndiaz.parquesbsas.model.Reclamo;

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
    public void doGetReclamos(int idParque, final boolean refreshData) {
        interactor.getReclamos(idParque, new BaseCallback<List<Reclamo>>() {
            @Override
            public void onSuccess(List<Reclamo> reclamos) {
                if (reclamos.isEmpty()) {
                    view.get().showEmptyContainer();
                } else {
                    if (!refreshData) {
                        view.get().showReclamos(reclamos);
                    } else {
                        view.get().refreshReclamos(reclamos);
                    }
                }
            }

            @Override
            public void onError(String message) {
                view.get().showEmptyContainer();
            }
        });
    }
}

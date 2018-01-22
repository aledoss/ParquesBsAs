package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaEstSaludContract;
import com.example.ndiaz.parquesbsas.model.EstacionSaludable;

import java.lang.ref.WeakReference;
import java.util.List;

public class ListaEstSaludPresenter extends BasePresenterImp
        implements ListaEstSaludContract.Presenter {

    private WeakReference<ListaEstSaludContract.View> view;
    private ListaEstSaludContract.Interactor interactor;

    public ListaEstSaludPresenter(ListaEstSaludContract.View view,
                                  ListaEstSaludContract.Interactor interactor) {
        this.view = new WeakReference<>(view);
        this.interactor = interactor;
    }


    @Override
    public void doGetEstSalud(int idParque) {
        interactor.getEstSalud(idParque, new BaseCallback<List<EstacionSaludable>>() {
            @Override
            public void onSuccess(List<EstacionSaludable> estSaludables) {
                view.get().showEstSalud(estSaludables);
            }

            @Override
            public void onError(String message) {
                view.get().showMessage(message);
            }
        });
    }
}

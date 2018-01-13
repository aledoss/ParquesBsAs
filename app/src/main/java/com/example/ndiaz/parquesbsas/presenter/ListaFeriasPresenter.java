package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaFeriasContract;
import com.example.ndiaz.parquesbsas.model.Feria;

import java.lang.ref.WeakReference;
import java.util.List;

public class ListaFeriasPresenter extends BasePresenterImp
        implements ListaFeriasContract.Presenter {

    private WeakReference<ListaFeriasContract.View> view;
    private ListaFeriasContract.Interactor interactor;

    public ListaFeriasPresenter(ListaFeriasContract.View view,
                                ListaFeriasContract.Interactor interactor) {
        this.view = new WeakReference<>(view);
        this.interactor = interactor;
    }


    @Override
    public void doGetFerias(int idParque) {
        interactor.getFerias(idParque, new BaseCallback<List<Feria>>() {
            @Override
            public void onSuccess(List<Feria> ferias) {
                view.get().showFerias(ferias);
            }

            @Override
            public void onError(String message) {
                view.get().showMessage(message);
            }
        });
    }
}

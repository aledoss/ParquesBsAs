package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.AgregarReclamoContract;
import com.example.ndiaz.parquesbsas.model.Reclamo;

import java.lang.ref.WeakReference;
import java.util.List;

public class AgregarReclamoPresenter extends BasePresenterImp
        implements AgregarReclamoContract.Presenter {

    private WeakReference<AgregarReclamoContract.View> agregarReclamoView;
    private AgregarReclamoContract.Interactor agregarReclamoInteractor;

    public AgregarReclamoPresenter(WeakReference<AgregarReclamoContract.View> agregarReclamoView,
                                   AgregarReclamoContract.Interactor agregarReclamoInteractor) {
        this.agregarReclamoView = agregarReclamoView;
        this.agregarReclamoInteractor = agregarReclamoInteractor;
    }

    @Override
    public void doGetReclamos() {
        agregarReclamoInteractor
                .getReclamos(new BaseCallback<List<Reclamo>>() {
                    @Override
                    public void onSuccess(List<Reclamo> reclamos) {
                        agregarReclamoView.get().fillReclamos(reclamos);
                    }

                    @Override
                    public void onError(String message) {
                        agregarReclamoView.get().showMessage(message);
                    }
                });
    }
}
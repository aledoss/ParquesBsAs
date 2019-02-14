package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.AgregarReclamoContract;
import com.example.ndiaz.parquesbsas.interactor.AgregarReclamoInteractor;
import com.example.ndiaz.parquesbsas.model.Reclamo;

import java.lang.ref.WeakReference;
import java.util.List;

public class AgregarReclamoPresenter extends BasePresenterImp
        implements AgregarReclamoContract.Presenter {

    private WeakReference<AgregarReclamoContract.View> agregarReclamoView;
    private AgregarReclamoContract.Interactor agregarReclamoInteractor;

    public AgregarReclamoPresenter(AgregarReclamoContract.View agregarReclamoView,
                                   AgregarReclamoContract.Interactor agregarReclamoInteractor) {
        this.agregarReclamoView = new WeakReference<>(agregarReclamoView);
        this.agregarReclamoInteractor = agregarReclamoInteractor;
    }

    @Override
    public void doGetReclamos() {
        agregarReclamoView.get().showProgressDialog();
        agregarReclamoInteractor
                .getReclamos(new BaseCallback<List<Reclamo>>() {
                    @Override
                    public void onSuccess(List<Reclamo> reclamos) {
                        agregarReclamoView.get().setReclamos(reclamos);
                        agregarReclamoView.get().hideProgressDialog();
                    }

                    @Override
                    public void onError(String message) {
                        agregarReclamoView.get().hideProgressDialog();
                        agregarReclamoView.get().showMessage(message);
                    }
                });
    }

    @Override
    public void doInsertReclamo(Reclamo reclamo) {
        agregarReclamoView.get().showProgressDialog();
        agregarReclamoInteractor
                .insertReclamo(reclamo, new BaseCallback<String>() {
                    @Override
                    public void onSuccess(String value) {
                        agregarReclamoView.get().hideProgressDialog();
                        agregarReclamoView.get().navegarAListaReclamos(value);
                    }

                    @Override
                    public void onError(String message) {
                        agregarReclamoView.get().hideProgressDialog();
                        //agregar al message la opcion de reintentar
                        agregarReclamoView.get().showMessage(message);
                    }
                });
    }

    @Override
    public void doInsertReclamoWithPhoto(Reclamo reclamo, String rutaImagen) {
        agregarReclamoView.get().showProgressDialog();
        agregarReclamoInteractor
                .uploadPhoto(reclamo, rutaImagen, new BaseCallback<String>() {
                    @Override
                    public void onSuccess(String value) {
                        agregarReclamoView.get().hideProgressDialog();
                        agregarReclamoView.get().navegarAListaReclamos(value);
                    }

                    @Override
                    public void onError(String message) {
                        agregarReclamoView.get().hideProgressDialog();
                        if (message.equals(AgregarReclamoInteractor.UPLOADING_PHOTO_ERROR)) {
                            agregarReclamoView.get().showRetryUploadingPhoto(reclamo);
                        } else {
                            agregarReclamoView.get().showMessage(message);
                        }
                    }
                });

    }
}
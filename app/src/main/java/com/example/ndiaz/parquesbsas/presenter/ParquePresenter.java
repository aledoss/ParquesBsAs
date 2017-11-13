package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ParqueContract;
import com.example.ndiaz.parquesbsas.model.ParqueComponente;

import java.lang.ref.WeakReference;
import java.util.List;

public class ParquePresenter extends BasePresenterImp implements ParqueContract.Presenter {

    private static final String TAG = ParquePresenter.class.getSimpleName();
    private WeakReference<ParqueContract.View> parqueView;
    private ParqueContract.Interactor parqueInteractor;

    public ParquePresenter(ParqueContract.View parqueView, ParqueContract.Interactor parqueInteractor) {
        this.parqueView = new WeakReference<>(parqueView);
        this.parqueInteractor = parqueInteractor;
    }

    public void doGetParqueComponents(int idParque) {
        parqueInteractor
                .getParqueComponents(idParque, new BaseCallback<List<ParqueComponente>>() {
                    @Override
                    public void onSuccess(List<ParqueComponente> componentes) {
                        parqueView.get().showParqueComponents(componentes);
                    }

                    @Override
                    public void onError(String message) {
                        parqueView.get().showEmptyContainer();
                        parqueView.get().showMessage(message);
                    }
                });
    }
}

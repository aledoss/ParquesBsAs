package com.example.ndiaz.parquesbsas.presenter;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ParqueContract;
import com.example.ndiaz.parquesbsas.model.ParqueComponentes;

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

    public void doGetParqueComponents() {
        parqueInteractor
                .getParqueComponents(new BaseCallback<List<ParqueComponentes>>() {
                    @Override
                    public void onSuccess(List<ParqueComponentes> componentes) {
                        parqueView.get().showParqueComponents(componentes);
                    }

                    @Override
                    public void onError(String message) {
                        parqueView.get().showMessage(message);
                        Log.e(TAG, "doGetParqueComponents, onError: " + message);
                    }
                });
    }
}

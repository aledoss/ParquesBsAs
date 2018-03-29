package com.example.ndiaz.parquesbsas.interactor;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaPuntosVerdesContract;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.PuntoVerde;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListaPuntosVerdesInteractor extends BaseInteractorImp
        implements ListaPuntosVerdesContract.Interactor {

    private static final String TAG = ListaPuntosVerdesInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;

    public ListaPuntosVerdesInteractor(NetworkServiceImp networkServiceImp) {
        this.networkServiceImp = networkServiceImp;
    }

    @Override
    public void getPuntosVerdes(int idParque, final BaseCallback<List<PuntoVerde>> callback) {
        networkServiceImp
                .getPuntosVerdes(idParque)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<List<PuntoVerde>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkResponse<List<PuntoVerde>> puntoVerdeNetworkResponse) {
                        String message = puntoVerdeNetworkResponse.getMessage();
                        callback.onSuccess(puntoVerdeNetworkResponse.getResponse());
                        Log.i(TAG, "getPuntosVerdes, onSuccess: " + message);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                        Log.e(TAG, "getPuntosVerdes, onError: ", e);
                    }
                });
    }
}

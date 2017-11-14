package com.example.ndiaz.parquesbsas.interactor;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.constants.HTTPConstants;
import com.example.ndiaz.parquesbsas.contract.ListaReclamosParqueContract;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Reclamo;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListaReclamosParqueInteractor extends BaseInteractorImp
        implements ListaReclamosParqueContract.Interactor {

    private static final String TAG = ListaReclamosParqueInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;

    public ListaReclamosParqueInteractor(NetworkServiceImp networkServiceImp) {
        this.networkServiceImp = networkServiceImp;
    }

    @Override
    public void getReclamos(int idParque, final BaseCallback<List<Reclamo>> callback) {
        networkServiceImp
                .getReclamosByParque(idParque)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<NetworkResponse<List<Reclamo>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull NetworkResponse<List<Reclamo>> listNetworkResponse) {
                        String message = listNetworkResponse.getMessage();
                        if (listNetworkResponse.getStatus() == HTTPConstants.STATUS_OK) {
                            callback.onSuccess(listNetworkResponse.getResponse());
                            Log.i(TAG, "getReclamos, onSuccess: " + message);
                        } else {
                            callback.onError(message);
                            Log.e(TAG, "getReclamos, onSuccess: " + message);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        String message = e.getMessage();
                        callback.onError(message);
                        Log.e(TAG, "getReclamos, onError: " + message);
                    }
                });
    }
}

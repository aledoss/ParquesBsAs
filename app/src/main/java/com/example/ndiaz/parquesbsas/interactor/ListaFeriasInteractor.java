package com.example.ndiaz.parquesbsas.interactor;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaFeriasContract;
import com.example.ndiaz.parquesbsas.model.Feria;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListaFeriasInteractor extends BaseInteractorImp
        implements ListaFeriasContract.Interactor {

    private static final String TAG = ListaFeriasInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;

    public ListaFeriasInteractor(NetworkServiceImp networkServiceImp) {
        this.networkServiceImp = networkServiceImp;
    }

    @Override
    public void getFerias(int idParque, final BaseCallback<List<Feria>> callback) {
        networkServiceImp
                .getferias(idParque)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<List<Feria>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkResponse<List<Feria>> feriaNetworkResponse) {
                        String message = feriaNetworkResponse.getMessage();
                        callback.onSuccess(feriaNetworkResponse.getResponse());
                        Log.i(TAG, "getferias, onSuccess: " + message);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                        Log.e(TAG, "getferias, onError: ", e);
                    }
                });
    }
}

package com.example.ndiaz.parquesbsas.interactor;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaEstSaludContract;
import com.example.ndiaz.parquesbsas.model.EstacionSaludable;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListaEstSaludInteractor extends BaseInteractorImp
        implements ListaEstSaludContract.Interactor {

    private static final String TAG = ListaEstSaludInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;

    public ListaEstSaludInteractor(NetworkServiceImp networkServiceImp) {
        this.networkServiceImp = networkServiceImp;
    }

    @Override
    public void getEstSalud(int idParque, final BaseCallback<List<EstacionSaludable>> callback) {
        networkServiceImp
                .getEstSaludables(idParque)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<List<EstacionSaludable>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkResponse<List<EstacionSaludable>> feriaNetworkResponse) {
                        String message = feriaNetworkResponse.getMessage();
                        callback.onSuccess(feriaNetworkResponse.getResponse());
                        Log.i(TAG, "getEstSalud, onSuccess: " + message);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                        Log.e(TAG, "getEstSalud, onError: ", e);
                    }
                });
    }
}

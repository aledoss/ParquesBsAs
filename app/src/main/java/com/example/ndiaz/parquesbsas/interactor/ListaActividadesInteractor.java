package com.example.ndiaz.parquesbsas.interactor;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaActividadesContract;
import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListaActividadesInteractor extends BaseInteractorImp
        implements ListaActividadesContract.Interactor {

    private static final String TAG = ListaActividadesInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;

    public ListaActividadesInteractor(NetworkServiceImp networkServiceImp) {
        this.networkServiceImp = networkServiceImp;
    }

    @Override
    public void getActividades(int idParque, final BaseCallback<List<Actividad>> callback) {
        networkServiceImp
                .getActividades(idParque)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<List<Actividad>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkResponse<List<Actividad>> actividadNetworkResponse) {
                        String message = actividadNetworkResponse.getMessage();
                        callback.onSuccess(actividadNetworkResponse.getResponse());
                        Log.i(TAG, "getActividades, onSuccess: " + message);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                        Log.e(TAG, "getActividades, onError: ", e);
                    }
                });
    }
}

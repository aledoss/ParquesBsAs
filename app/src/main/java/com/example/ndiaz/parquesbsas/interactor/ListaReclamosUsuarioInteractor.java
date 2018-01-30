package com.example.ndiaz.parquesbsas.interactor;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.constants.HTTPConstants;
import com.example.ndiaz.parquesbsas.contract.ListaReclamosUsuarioContract;
import com.example.ndiaz.parquesbsas.helpers.ReclamoFechaBuilder;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Reclamo;
import com.example.ndiaz.parquesbsas.model.ReclamoFecha;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListaReclamosUsuarioInteractor extends BaseInteractorImp
        implements ListaReclamosUsuarioContract.Interactor {

    private static final String TAG = ListaReclamosUsuarioInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;
    private ReclamoFechaBuilder reclamoFechaBuilder;

    public ListaReclamosUsuarioInteractor(NetworkServiceImp networkServiceImp) {
        this.networkServiceImp = networkServiceImp;
    }

    @Override
    public void getReclamosFecha(int idUsuario, final BaseCallback<List<ReclamoFecha>> callback) {
        networkServiceImp
                .getReclamosByUsuario(idUsuario)
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
                            reclamoFechaBuilder = new ReclamoFechaBuilder();
                            callback.onSuccess(reclamoFechaBuilder.build(listNetworkResponse.getResponse()));
                            Log.i(TAG, "getReclamosFecha, onSuccess: " + message);
                        } else {
                            callback.onError(message);
                            Log.e(TAG, "getReclamosFecha, onSuccess: " + message);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        String message = e.getMessage();
                        callback.onError(message);
                        Log.e(TAG, "getReclamosFecha, onError: " + message);
                    }
                });
    }
}

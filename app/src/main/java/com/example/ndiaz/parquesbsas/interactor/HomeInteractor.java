package com.example.ndiaz.parquesbsas.interactor;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.constants.HTTPConstants;
import com.example.ndiaz.parquesbsas.contract.HomeContract;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeInteractor extends BaseInteractorImp implements HomeContract.Interactor {

    private static final String TAG = HomeInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;
    private RXDBInteractor rxdbInteractor;

    public HomeInteractor(NetworkServiceImp networkServiceImp, RXDBInteractor rxdbInteractor) {
        this.networkServiceImp = networkServiceImp;
        this.rxdbInteractor = rxdbInteractor;
    }

    @Override
    public void getParques(final BaseCallback<List<Parque>> callback) {
        rxdbInteractor
                .getParques()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Parque>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Parque> parques) {
                        if (parques == null || parques.isEmpty()) {
                            getParquesFromNetwork(callback);
                        } else {
                            callback.onSuccess(parques);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getParquesFromNetwork(callback);
                        Log.e(TAG, "getParques, onError: " + e.getMessage());
                    }
                });
    }

    private void getParquesFromNetwork(final BaseCallback<List<Parque>> callback) {
        networkServiceImp
                .getParques()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<List<Parque>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull NetworkResponse<List<Parque>> parques) {
                        callback.onSuccess(parques.getResponse());
                        rxdbInteractor.saveParques(parques.getResponse());
                        Log.i(TAG, "getParquesFromNetwork: " + parques.getMessage());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callback.onError(e.getMessage());
                        Log.e(TAG, "getParquesFromNetwork, onError: " + e.getMessage());
                    }
                });
    }

    @Override
    public void getParqueNetwork(int parqueId, final BaseCallback<Parque> callback) {
        networkServiceImp
                .getParque(parqueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<Parque>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkResponse<Parque> parqueNetworkResponse) {
                        String message = parqueNetworkResponse.getMessage();
                        if (parqueNetworkResponse.status == HTTPConstants.STATUS_OK) {
                            callback.onSuccess(parqueNetworkResponse.getResponse());
                            Log.i(TAG, "getParqueNetwork: " + message);
                        } else {
                            callback.onError(message);
                            Log.e(TAG, "getParqueNetwork: " + message);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 16/08/2018   verifyConnection(e); // e instanceOf SocketTimeoutException
                        callback.onError(e.getMessage());
                        Log.e(TAG, "getParqueNetwork, onError: " + e.getMessage());
                    }
                });
    }
}

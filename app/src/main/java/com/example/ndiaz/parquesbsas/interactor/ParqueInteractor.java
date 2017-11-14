package com.example.ndiaz.parquesbsas.interactor;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.constants.HTTPConstants;
import com.example.ndiaz.parquesbsas.contract.ParqueContract;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.ParqueComponente;
import com.example.ndiaz.parquesbsas.model.ParqueComponentes.ParqueComponentesFactory;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ParqueInteractor extends BaseInteractorImp implements ParqueContract.Interactor {

    private static final String TAG = ParqueInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;

    public ParqueInteractor(NetworkServiceImp networkServiceImp) {
        this.networkServiceImp = networkServiceImp;
    }

    @Override
    public void getParqueComponents(final int idParque, final BaseCallback<List<ParqueComponente>> callback) {
        networkServiceImp
                .getParqueComponentes(idParque)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<List<ParqueComponente>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull NetworkResponse<List<ParqueComponente>> parqueComponenteNetworkResponse) {
                        String message = parqueComponenteNetworkResponse.getMessage();
                        if (parqueComponenteNetworkResponse.status == HTTPConstants.STATUS_OK) {
                            ParqueComponentesFactory factory = new ParqueComponentesFactory();
                            List<ParqueComponente> parqueComponentes = factory.getParqueComponentes(
                                    parqueComponenteNetworkResponse.getResponse(), idParque);

                            callback.onSuccess(parqueComponentes);
                            Log.i(TAG, "getParqueComponents: " + message);
                        } else {
                            callback.onError(message);
                            Log.e(TAG, "getParqueComponents: " + message);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callback.onError(e.getMessage());
                        Log.e(TAG, "getParqueComponents, onError: " + e.getMessage());
                    }
                });
    }
}

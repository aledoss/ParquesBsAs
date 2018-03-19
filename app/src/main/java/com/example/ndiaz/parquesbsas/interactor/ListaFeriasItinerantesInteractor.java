package com.example.ndiaz.parquesbsas.interactor;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaFeriasItinerantesContract;
import com.example.ndiaz.parquesbsas.model.FeriaItinerante;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListaFeriasItinerantesInteractor extends BaseInteractorImp
        implements ListaFeriasItinerantesContract.Interactor {

    private static final String TAG = ListaFeriasItinerantesInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;

    public ListaFeriasItinerantesInteractor(NetworkServiceImp networkServiceImp) {
        this.networkServiceImp = networkServiceImp;
    }

    @Override
    public void getFeriasItinerantes(int idParque, final BaseCallback<List<FeriaItinerante>> callback) {
        networkServiceImp
                .getFeriasItinerantes(idParque)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<List<FeriaItinerante>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkResponse<List<FeriaItinerante>> feriaNetworkResponse) {
                        String message = feriaNetworkResponse.getMessage();
                        callback.onSuccess(feriaNetworkResponse.getResponse());
                        Log.i(TAG, "getFeriasItinerantes, onSuccess: " + message);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                        Log.e(TAG, "getFeriasItinerantes, onError: ", e);
                    }
                });
    }
}

package com.example.ndiaz.parquesbsas.interactor;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.FiltroParqueContract;
import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.model.Feria;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.ParqueFilter;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FiltroParqueInteractor extends BaseInteractorImp
        implements FiltroParqueContract.Interactor {

    private static final String TAG = FiltroParqueInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;

    public FiltroParqueInteractor(NetworkServiceImp networkServiceImp) {
        this.networkServiceImp = networkServiceImp;
    }


    @Override
    public void getActividadesToFilter(BaseCallback<List<Actividad>> callback) {
        networkServiceImp
                .getActividadesToFilter()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<List<Actividad>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(NetworkResponse<List<Actividad>> listNetworkResponse) {
                        onSuccessDefault(listNetworkResponse, TAG, "getActividadesToFilter",
                                callback);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorDefault(e, TAG, "getActividadesToFilter", callback);
                    }
                });
    }

    @Override
    public void getFeriasToFilter(BaseCallback<List<Feria>> callback) {
        networkServiceImp
                .getFeriasToFilter()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<List<Feria>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(NetworkResponse<List<Feria>> listNetworkResponse) {
                        onSuccessDefault(listNetworkResponse, TAG, "getFeriasToFilter",
                                callback);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorDefault(e, TAG, "getFeriasToFilter", callback);
                    }
                });
    }

    @Override
    public void filterParques(ParqueFilter filter, BaseCallback<List<Parque>> callback) {
        networkServiceImp
                .filterParques(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<List<Parque>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(NetworkResponse<List<Parque>> listNetworkResponse) {
                        onSuccessDefault(listNetworkResponse, TAG, "filterParques", callback);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorDefault(e, TAG, "filterParques", callback);
                    }
                });
    }
}

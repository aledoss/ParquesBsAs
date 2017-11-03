package com.example.ndiaz.parquesbsas.interactor;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.CreateUserContract;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.TiposDocumento;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;
import com.example.ndiaz.parquesbsas.preferences.PreferencesRepository;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateUserInteractor extends BaseInteractorImp
        implements CreateUserContract.Interactor {

    private PreferencesRepository preferencesRepository;
    private NetworkServiceImp networkServiceImp;

    public CreateUserInteractor(PreferencesRepository preferencesRepository, NetworkServiceImp networkServiceImp) {
        this.preferencesRepository = preferencesRepository;
        this.networkServiceImp = networkServiceImp;
    }


    @Override
    public void createUser(Usuario usuario, final BaseCallback callback) {
        networkServiceImp
                .createUser(usuario)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkResponse networkResponse) {
                        callback.onSuccess(networkResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }

    @Override
    public void getDocTypes(final BaseCallback<NetworkResponse<List<TiposDocumento>>> callback) {
        networkServiceImp
                .getDocTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<List<TiposDocumento>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkResponse<List<TiposDocumento>> tiposDocumentoNetworkResponse) {
                        callback.onSuccess(tiposDocumentoNetworkResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }
}

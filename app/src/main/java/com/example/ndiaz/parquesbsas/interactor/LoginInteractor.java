package com.example.ndiaz.parquesbsas.interactor;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.LoginContract;
import com.example.ndiaz.parquesbsas.gsonresult.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;
import com.example.ndiaz.parquesbsas.preferences.PreferencesRepository;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginInteractor extends BaseInteractorImp implements LoginContract.Interactor {

    private PreferencesRepository preferencesRepository;
    private NetworkServiceImp networkServiceImp;

    public LoginInteractor(PreferencesRepository preferencesRepository, NetworkServiceImp networkServiceImp) {
        this.preferencesRepository = preferencesRepository;
        this.networkServiceImp = networkServiceImp;
    }

    @Override
    public void login(Usuario usuario, final BaseCallback callback) {
        networkServiceImp
                .getUser(usuario)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull NetworkResponse usuario) {
                        callback.onSuccess(usuario);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });

        //addDisposable();
    }

}

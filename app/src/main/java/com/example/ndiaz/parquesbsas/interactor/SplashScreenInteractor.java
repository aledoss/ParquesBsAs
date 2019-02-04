package com.example.ndiaz.parquesbsas.interactor;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.SingleCallback;
import com.example.ndiaz.parquesbsas.contract.SplashScreenContract;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;
import com.example.ndiaz.parquesbsas.repositories.UserDataRepository;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashScreenInteractor extends BaseInteractorImp implements SplashScreenContract.Interactor {

    private static final String TAG = SplashScreenInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;
    private UserDataRepository userDataRepository;

    public SplashScreenInteractor(NetworkServiceImp networkServiceImp, UserDataRepository userDataRepository) {
        this.networkServiceImp = networkServiceImp;
        this.userDataRepository = userDataRepository;
    }

    @Override
    public void login(Usuario usuario, final BaseCallback<Usuario> callback) {
        networkServiceImp
                .getUser(usuario)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<Usuario>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(@NonNull NetworkResponse<Usuario> response) {
                        onSuccessDefault(response, TAG, "login", callback);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onErrorDefault(e, TAG, "login", callback);
                    }
                });
    }

    @Override
    public void getLoginData(SingleCallback<Usuario> callback) {
        Usuario usuario = ParquesApplication.getInstance().getUser();
        if (usuario != null) {
            callback.onSuccess(usuario);
        } else {
            addDisposable(userDataRepository.getUserData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(callback::onSuccess)
            );
        }

    }

    @Override
    public void loginWithGoogle(Usuario usuario, BaseCallback<Usuario> callback) {
        networkServiceImp
                .loginWithGoogle(usuario)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<Usuario>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(NetworkResponse<Usuario> networkResponse) {
                        onSuccessDefault(networkResponse, TAG, "loginWithGoogle", callback);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorDefault(e, TAG, "loginWithGoogle", callback);
                    }
                });
    }
}

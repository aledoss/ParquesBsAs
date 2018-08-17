package com.example.ndiaz.parquesbsas.interactor;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.SingleCallback;
import com.example.ndiaz.parquesbsas.contract.SplashScreenContract;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;
import com.example.ndiaz.parquesbsas.preferences.DefaultPreferencesRepository;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashScreenInteractor extends BaseInteractorImp implements SplashScreenContract.Interactor {

    private static final String TAG = SplashScreenInteractor.class.getSimpleName();
    private DefaultPreferencesRepository defaultPreferencesRepository;
    private NetworkServiceImp networkServiceImp;

    public SplashScreenInteractor(DefaultPreferencesRepository defaultPreferencesRepository, NetworkServiceImp networkServiceImp) {
        this.defaultPreferencesRepository = defaultPreferencesRepository;
        this.networkServiceImp = networkServiceImp;
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
            addDisposable(getUserEmail()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(email -> getUserPassword()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(password -> callback.onSuccess(new Usuario(email, password))))
            );
        }

    }

    private Single<String> getUserEmail() {
        return Single.fromCallable(() -> defaultPreferencesRepository.getUserEmail());
    }

    private Single<String> getUserPassword() {
        return Single.fromCallable(() -> defaultPreferencesRepository.getUserPassword());
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

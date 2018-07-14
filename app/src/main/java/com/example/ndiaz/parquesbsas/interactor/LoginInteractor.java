package com.example.ndiaz.parquesbsas.interactor;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.SingleCallback;
import com.example.ndiaz.parquesbsas.contract.LoginContract;
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

public class LoginInteractor extends BaseInteractorImp implements LoginContract.Interactor {

    private static final String TAG = LoginInteractor.class.getSimpleName();
    private DefaultPreferencesRepository defaultPreferencesRepository;
    private NetworkServiceImp networkServiceImp;

    public LoginInteractor(DefaultPreferencesRepository defaultPreferencesRepository, NetworkServiceImp networkServiceImp) {
        this.defaultPreferencesRepository = defaultPreferencesRepository;
        this.networkServiceImp = networkServiceImp;
    }

    @Override
    public void login(Usuario usuario, final BaseCallback<Usuario> callback) {
        networkServiceImp
                .getUser(usuario)
                .subscribeOn(Schedulers.io())
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
    public void updateUserData(Usuario usuario) {
        defaultPreferencesRepository.setUserEmail(usuario.getEmail());
        defaultPreferencesRepository.setUserPassword(usuario.getPassword());
    }

    @Override
    public void isAutoLoginEnabled(SingleCallback<Boolean> callback) {
        addDisposable(getIsAutoLoginEnabled()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isAutoLoginEnabled -> callback.onSuccess(isAutoLoginEnabled))
        );
    }

    @Override
    public void getLoginData(SingleCallback<Usuario> callback) {
        addDisposable(getUserEmail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(email -> getUserPassword()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(password -> callback.onSuccess(new Usuario(email, password))))
        );
    }

    private Single<String> getUserEmail() {
        return Single.fromCallable(() -> defaultPreferencesRepository.getUserEmail());
    }

    private Single<String> getUserPassword() {
        return Single.fromCallable(() -> defaultPreferencesRepository.getUserPassword());
    }

    private Single<Boolean> getIsAutoLoginEnabled() {
        return Single.fromCallable(() -> defaultPreferencesRepository.getIsAutoLoginEnabled());
    }
}

package com.example.ndiaz.parquesbsas.interactor;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.SingleCallback;
import com.example.ndiaz.parquesbsas.contract.LoginContract;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;
import com.example.ndiaz.parquesbsas.repositories.UserDataRepository;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginInteractor extends BaseInteractorImp implements LoginContract.Interactor {

    private static final String TAG = LoginInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;
    private UserDataRepository userDataRepository;

    public LoginInteractor(NetworkServiceImp networkServiceImp,
                           UserDataRepository userDataRepository) {
        this.networkServiceImp = networkServiceImp;
        this.userDataRepository = userDataRepository;
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
        userDataRepository.saveUserData(usuario);
    }

    /*@Override
    public void isAutoLoginEnabled(SingleCallback<Boolean> callback) {
        addDisposable(getIsAutoLoginEnabled()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isAutoLoginEnabled -> callback.onSuccess(isAutoLoginEnabled))
        );
    }*/

    @Override
    public void getLoginData(SingleCallback<Usuario> callback) {
        addDisposable(userDataRepository.getUserData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onSuccess)
        );
    }

    @Override
    public void recuperarContrasenia(String email, BaseCallback<String> callback) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        networkServiceImp
                .recoverPassword(usuario)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(NetworkResponse<String> networkResponse) {
                        onSuccessDefault(networkResponse, TAG, "recuperarContrasenia", callback);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorDefault(e, TAG, "recuperarContrasenia", callback);
                    }
                });
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

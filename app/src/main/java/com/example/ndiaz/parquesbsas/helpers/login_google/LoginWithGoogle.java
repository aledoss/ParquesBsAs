package com.example.ndiaz.parquesbsas.helpers.login_google;

import android.util.Log;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.constants.HTTPConstants;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;
import com.example.ndiaz.parquesbsas.preferences.DefaultPreferencesRepository;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginWithGoogle implements LoginWithGoogleListener {

    private static final String TAG = LoginWithGoogle.class.getSimpleName();
    private DefaultPreferencesRepository defaultPreferencesRepository;
    private NetworkServiceImp networkServiceImp;
    private CompositeDisposable compositeDisposable;

    public LoginWithGoogle(DefaultPreferencesRepository defaultPreferencesRepository, NetworkServiceImp networkServiceImp) {
        this.defaultPreferencesRepository = defaultPreferencesRepository;
        this.networkServiceImp = networkServiceImp;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void doLogin(GoogleSignInAccount account, OnUserLoggedWithGoogleListener listener) {
        Usuario usuario = getUserFromGoogleSignInAccount(account);

        networkServiceImp
                .loginWithGoogle(usuario)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<Usuario>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(NetworkResponse<Usuario> response) {
                        handleSuccessServerResponse(response, listener, "doLogin");
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleErrorServerResponse(e, listener, "doLogin");
                    }
                });
    }

    @Override
    public void doVinculate(GoogleSignInAccount account, Integer idUsuario, OnUserLoggedWithGoogleListener listener) {
        Usuario usuario = getUserFromGoogleSignInAccount(account, idUsuario);

        networkServiceImp
                .vinculateWithGoogle(usuario)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<Usuario>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(NetworkResponse<Usuario> response) {
                        handleSuccessServerResponse(response, listener, "doVinculate");
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleErrorServerResponse(e, listener, "doVinculate");
                    }
                });
    }

    private void handleSuccessServerResponse(NetworkResponse<Usuario> response,
                                             OnUserLoggedWithGoogleListener listener, String methodName) {
        String message = response.getMessage();
        if (response.getStatus() == HTTPConstants.STATUS_OK) {
            Usuario usuarioLogueado = response.getResponse();
            ParquesApplication.getInstance().setUser(usuarioLogueado);
            ParquesApplication.getInstance().setLoggedWithGoogle(true);
            listener.onLoginWithGoogleSuccess(usuarioLogueado);

            Log.i(TAG, methodName + ", onSuccess: " + message);
        } else {
            listener.onLoginWithGoogleError(message);
            Log.e(TAG, methodName + ", onSuccess: " + message);
        }
    }

    private void handleErrorServerResponse(Throwable e, OnUserLoggedWithGoogleListener listener,
                                           String methodName) {
        String message = e.getMessage();
        listener.onLoginWithGoogleError(message);
        Log.e(TAG, methodName + ", onError: " + message, e);
    }

    private Usuario getUserFromGoogleSignInAccount(GoogleSignInAccount account) {
        return getUserFromGoogleSignInAccount(account, null);
    }

    private Usuario getUserFromGoogleSignInAccount(GoogleSignInAccount account, Integer idUsuario) {
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        usuario.setGoogleId(account.getId());
        usuario.setEmail(account.getEmail());
        usuario.setNombre(account.getGivenName());
        usuario.setApellido(account.getFamilyName());
        return usuario;
    }

    @Override
    public void updateUserData(Usuario usuario) {
        defaultPreferencesRepository.setUserEmail(usuario.getEmail());
        defaultPreferencesRepository.setUserPassword(usuario.getPassword());
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
    }
}

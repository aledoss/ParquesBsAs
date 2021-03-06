package com.example.ndiaz.parquesbsas.interactor;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.EmptyCallback;
import com.example.ndiaz.parquesbsas.contract.PerfilUsuarioContract;
import com.example.ndiaz.parquesbsas.model.Documento;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.model.UsuarioPassword;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;
import com.example.ndiaz.parquesbsas.preferences.DefaultPreferencesRepository;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PerfilUsuarioInteractor extends BaseInteractorImp
        implements PerfilUsuarioContract.Interactor {

    private static final String TAG = PerfilUsuarioInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;
    private DefaultPreferencesRepository defaultPreferencesRepository;

    public PerfilUsuarioInteractor(NetworkServiceImp networkServiceImp, DefaultPreferencesRepository defaultDefaultPreferencesRepository) {
        this.networkServiceImp = networkServiceImp;
        this.defaultPreferencesRepository = defaultDefaultPreferencesRepository;
    }

    @Override
    public void updateName(int idUsuario, String nombre, String apellido, EmptyCallback callback) {
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);

        networkServiceImp
                .updateUserName(usuario)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(NetworkResponse networkResponse) {
                        onSuccessEmpty(networkResponse, TAG, "updateName", callback);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorEmpty(e, TAG, "updateName", callback);
                    }
                });
    }

    @Override
    public void updateDoc(int idUsuario, Documento documento, EmptyCallback callback) {
        networkServiceImp
                .updateUserDocument(idUsuario, documento)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(NetworkResponse networkResponse) {
                        onSuccessEmpty(networkResponse, TAG, "updateDoc", callback);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorEmpty(e, TAG, "updateDoc", callback);
                    }
                });
    }

    @Override
    public void updatePassword(UsuarioPassword usuario, EmptyCallback callback) {
        networkServiceImp
                .updateUserPassword(usuario)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse>() {
                    String methodName = "updatePassword";

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(NetworkResponse networkResponse) {
                        onSuccessEmpty(networkResponse, TAG, methodName, callback);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorEmpty(e, TAG, methodName, callback);
                    }
                });
    }

    @Override
    public void deleteCuenta(int idUsuario, BaseCallback<String> callback) {
        networkServiceImp
                .deleteUser(new Usuario(idUsuario))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse>() {
                    String methodName = "deleteCuenta";

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(NetworkResponse networkResponse) {
                        onSuccessDefault(networkResponse, TAG, methodName, callback);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorDefault(e, TAG, methodName, callback);
                    }
                });
    }

    @Override
    public void getDocTypes(final BaseCallback<List<Documento>> callback) {
        networkServiceImp
                .getDocTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<List<Documento>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(NetworkResponse<List<Documento>> tiposDocumentoNetworkResponse) {
                        onSuccessDefault(tiposDocumentoNetworkResponse, TAG, "getDocTypes", callback);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorDefault(e, TAG, e.getMessage(), callback);
                    }
                });
    }

    @Override
    public void cleanAutoLoginUserData() {
        defaultPreferencesRepository.setUserEmail("");
        defaultPreferencesRepository.setUserPassword("");
        ParquesApplication.getInstance().clearUser();
    }
}

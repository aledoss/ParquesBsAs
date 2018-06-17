package com.example.ndiaz.parquesbsas.interactor;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.constants.HTTPConstants;
import com.example.ndiaz.parquesbsas.contract.ListaEncuestasParqueContract;
import com.example.ndiaz.parquesbsas.model.Calificacion;
import com.example.ndiaz.parquesbsas.model.CalificacionEncuesta;
import com.example.ndiaz.parquesbsas.model.Encuesta;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListaEncuestasParqueInteractor extends BaseInteractorImp
        implements ListaEncuestasParqueContract.Interactor {

    private static final String TAG = ListaEncuestasParqueInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;

    public ListaEncuestasParqueInteractor(NetworkServiceImp networkServiceImp) {
        this.networkServiceImp = networkServiceImp;
    }

    @Override
    public void getEncuestasParaCalificar(int idParque, int idUsuario, BaseCallback<List<Encuesta>> callback) {
        networkServiceImp
                .getEncuestasParaCalificar(idParque, idUsuario)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<NetworkResponse<List<Encuesta>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkResponse<List<Encuesta>> listNetworkResponse) {
                        String message = listNetworkResponse.getMessage();
                        if (listNetworkResponse.getStatus() == HTTPConstants.STATUS_OK) {
                            callback.onSuccess(listNetworkResponse.getResponse());
                            Log.i(TAG, "getEncuestasParaCalificar, onSuccess: " + message);
                        } else {
                            callback.onError(message);
                            Log.e(TAG, "getEncuestasParaCalificar, onSuccess: " + message);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = e.getMessage();
                        callback.onError(message);
                        Log.e(TAG, "getEncuestasParaCalificar, onError: " + message);
                    }
                });
    }

    @Override
    public void getCalificaciones(BaseCallback<List<Calificacion>> callback) {
        networkServiceImp
                .getCalificaciones()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<NetworkResponse<List<Calificacion>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkResponse<List<Calificacion>> listNetworkResponse) {
                        String message = listNetworkResponse.getMessage();
                        if (listNetworkResponse.getStatus() == HTTPConstants.STATUS_OK) {
                            callback.onSuccess(listNetworkResponse.getResponse());
                            Log.i(TAG, "getCalificaciones, onSuccess: " + message);
                        } else {
                            callback.onError(message);
                            Log.e(TAG, "getCalificaciones, onSuccess: " + message);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = e.getMessage();
                        callback.onError(message);
                        Log.e(TAG, "getCalificaciones, onError: " + message);
                    }
                });
    }

    @Override
    public void getEncuestasByParque(int idParque, final BaseCallback<List<Encuesta>> callback) {
        networkServiceImp
                .getEncuestasByParque(idParque)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<NetworkResponse<List<Encuesta>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull NetworkResponse<List<Encuesta>> listNetworkResponse) {
                        String message = listNetworkResponse.getMessage();
                        if (listNetworkResponse.getStatus() == HTTPConstants.STATUS_OK) {
                            callback.onSuccess(listNetworkResponse.getResponse());
                            Log.i(TAG, "getEncuestasByParque, onSuccess: " + message);
                        } else {
                            callback.onError(message);
                            Log.e(TAG, "getEncuestasByParque, onSuccess: " + message);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        String message = e.getMessage();
                        callback.onError(message);
                        Log.e(TAG, "getEncuestasByParque, onError: " + message);
                    }
                });
    }



    @Override
    public void getEstadisticasEncuesta(int idParque, int idEncuesta, BaseCallback<Calificacion> callback) {
        networkServiceImp
                .getEstadisticasEncuesta(idParque, idEncuesta)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<NetworkResponse<Calificacion>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkResponse<Calificacion> calificacionNetworkResponse) {
                        String message = calificacionNetworkResponse.getMessage();
                        if (calificacionNetworkResponse.getStatus() == HTTPConstants.STATUS_OK) {
                            callback.onSuccess(calificacionNetworkResponse.getResponse());
                            Log.i(TAG, "getEncuestasByParque, onSuccess: " + message);
                        } else {
                            callback.onError(message);
                            Log.e(TAG, "getEncuestasByParque, onSuccess: " + message);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = e.getMessage();
                        callback.onError(message);
                        Log.e(TAG, "getEncuestasByParque, onError: " + message, e);
                    }
                });
    }

    @Override
    public void createCalificacionEncuesta(CalificacionEncuesta calificacionEncuesta, BaseCallback<String> callback) {
        networkServiceImp
                .createCalificacionEncuesta(calificacionEncuesta)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<NetworkResponse<Boolean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkResponse<Boolean> createCalificacionEncuestaNetRespo) {
                        String message = createCalificacionEncuestaNetRespo.getMessage();
                        if (createCalificacionEncuestaNetRespo.getStatus() == HTTPConstants.STATUS_OK) {
                            callback.onSuccess(message);
                            Log.i(TAG, "createCalificacionEncuesta, onSuccess: " + message);
                        } else {
                            callback.onError(message);
                            Log.e(TAG, "createCalificacionEncuesta, onSuccess: " + message);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorDefault(e, TAG, "createCalificacionEncuesta", callback);
                    }
                });
    }
}

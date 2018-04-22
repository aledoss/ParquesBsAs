package com.example.ndiaz.parquesbsas.interactor;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.SingleCallback;
import com.example.ndiaz.parquesbsas.contract.DescGralContract;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.ParqueLikeBody;
import com.example.ndiaz.parquesbsas.model.parquelike.ParqueLike;
import com.example.ndiaz.parquesbsas.model.parquelike.ParqueLikeDecrease;
import com.example.ndiaz.parquesbsas.model.parquelike.ParqueLikeDefault;
import com.example.ndiaz.parquesbsas.model.parquelike.ParqueLikeIncrease;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DescGralInteractor extends BaseInteractorImp implements DescGralContract.Interactor {

    private static final String TAG = DescGralInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;
    private SharedPreferences sharedPreferences;

    public DescGralInteractor(NetworkServiceImp networkServiceImp, SharedPreferences sharedPreferences) {
        this.networkServiceImp = networkServiceImp;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void updateParqueLike(final int idParque, final int idUsuario, final boolean increase,
                                 ParqueLike parqueLike, final BaseCallback callback) {
        ParqueLikeBody parqueLikeBody;
        boolean defaultParque = false;
        boolean decreaseLike = false;
        boolean increaseLike = false;
        boolean increaseHate = false;
        boolean decreaseHate = false;
        if (parqueLike instanceof ParqueLikeDefault) {
            if (increase) {
                increaseLike = true;
            } else {
                increaseHate = true;
            }
        } else if (parqueLike instanceof ParqueLikeIncrease) {
            if (increase) {
                decreaseLike = true;
                defaultParque = true;
            } else {
                increaseHate = true;
                decreaseLike = true;
            }
        } else if (parqueLike instanceof ParqueLikeDecrease) {
            if (increase) {
                decreaseHate = true;
                increaseLike = true;
            } else {
                decreaseHate = true;
                defaultParque = true;
            }
        }

        final boolean finalDefaultParque = defaultParque;
        parqueLikeBody = new ParqueLikeBody(idParque, increaseLike, decreaseLike, increaseHate, decreaseHate);
        networkServiceImp
                .updateParqueLike(parqueLikeBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull NetworkResponse networkResponse) {
                        String message = networkResponse.getMessage();

                        updateSharedPrefParqueLike(idParque, idUsuario, increase, finalDefaultParque);
                        callback.onSuccess(message);
                        Log.i(TAG, "updateParqueLike: " + message);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callback.onError(e.getMessage());
                        Log.e(TAG, "updateParqueLike, onError: " + e.getMessage());
                    }
                });

    }

    @Override
    public void updateSharedPrefParqueLike(int idParque, int idUsuario, boolean increase,
                                           boolean defaultParque) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String key = getKeySharedPrefParqueLike(idParque, idUsuario);
        if (defaultParque){
            editor.putString(key, "");
        }else{
            editor.putString(key, buildSharedPrefValue(idParque, idUsuario, increase));
        }
        editor.apply();
    }

    @Override
    public void getParqueLikeStatus(int idParque, int idUsuario, SingleCallback<String> callback) {
        callback.onSuccess(getSharedPrefParqueLike(idParque, idUsuario));
    }

    private String buildSharedPrefValue(int idParque, int idUsuario, boolean incr) {
        int increase = incr ? 1 : 0;
        return String.valueOf(idParque) + "," + String.valueOf(idUsuario) + "," + increase;
    }

    /**
     *
     * @return  idParque, idUsuario, increase (0/1), ej: "4,2,1"
     *      -empty String if it's default status.
     *      -increaseActive -> "x,x,1"
     *      -decreaseActive -> "x,x,0"
     * @param idParque
     * @param idUsuario
     */
    private String getSharedPrefParqueLike(int idParque, int idUsuario) {
        return sharedPreferences.getString(getKeySharedPrefParqueLike(idParque, idUsuario), "");
    }

    private String getKeySharedPrefParqueLike(int idParque, int idUsuario){
        return idParque + "," + idUsuario;
    }

}

package com.example.ndiaz.parquesbsas.interactor;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.EmptyCallback;
import com.example.ndiaz.parquesbsas.constants.HTTPConstants;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.database.DBHelper;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseInteractorImp implements BaseInteractor {

    private CompositeDisposable compositeDisposable;
    private DBHelper dbHelper;
    private NetworkServiceImp networkServiceImp;

    public BaseInteractorImp() {
    }

    public BaseInteractorImp(NetworkServiceImp networkServiceImp) {
        this.networkServiceImp = networkServiceImp;
    }

    public BaseInteractorImp(DBHelper dbHelper, NetworkServiceImp networkServiceImp) {
        this.dbHelper = dbHelper;
        this.networkServiceImp = networkServiceImp;
    }

    private CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null){
            this.compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public NetworkServiceImp getNetworkServiceImp() {
        return networkServiceImp;
    }

    public void setNetworkServiceImp(NetworkServiceImp networkServiceImp) {
        this.networkServiceImp = networkServiceImp;
    }

    @Override
    public void unsubscribeAll() {
        if (compositeDisposable != null){
            compositeDisposable.clear();
        }
    }

    public void addDisposable(Disposable disposable) {
        getCompositeDisposable().add(disposable);
    }

    <T> void onErrorDefault(Throwable e, String tag, String methodName, BaseCallback<T> callback) {
        String message = e.getMessage();
        callback.onError(message);
        Log.e(tag, methodName + ", onError: " + message, e);
    }

    <T> void onErrorEmpty(Throwable e, String tag, String methodName, EmptyCallback callback) {
        String message = e.getMessage();
        callback.onError(message);
        Log.e(tag, methodName + ", onError: " + message, e);
    }

    <T> void onSuccessDefault(NetworkResponse<T> response, String tag, String methodName, BaseCallback<T> callback) {
        String message = response.getMessage();
        if (response.getStatus() == HTTPConstants.STATUS_OK) {
            callback.onSuccess(response.getResponse());
            Log.i(tag, methodName + ", onSuccess: " + message);
        } else {
            callback.onError(message);
            Log.e(tag, methodName + ", onSuccess: " + message);
        }
    }

    <T> void onSuccessEmpty(NetworkResponse response, String tag, String methodName, EmptyCallback callback) {
        String message = response.getMessage();
        if (response.getStatus() == HTTPConstants.STATUS_OK) {
            callback.onSuccess();
            Log.i(tag, methodName + ", onSuccess: " + message);
        } else {
            callback.onError(message);
            Log.e(tag, methodName + ", onSuccess: " + message);
        }
    }

}

package com.example.ndiaz.parquesbsas.interactor;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.constants.HTTPConstants;
import com.example.ndiaz.parquesbsas.contract.AgregarReclamoContract;
import com.example.ndiaz.parquesbsas.helpers.FTPManager;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Reclamo;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.ndiaz.parquesbsas.constants.Constants.FTP_ANDROID_IMAGE_DIRECTORY;

public class AgregarReclamoInteractor extends BaseInteractorImp
        implements AgregarReclamoContract.Interactor {

    private static final String TAG = AgregarReclamoInteractor.class.getSimpleName();
    public static final String UPLOADING_PHOTO_ERROR = "1";
    private NetworkServiceImp networkServiceImp;

    public AgregarReclamoInteractor(NetworkServiceImp networkServiceImp1) {
        this.networkServiceImp = networkServiceImp1;
    }

    @Override
    public void getReclamos(final BaseCallback<List<Reclamo>> callback) {
        networkServiceImp
                .getReclamos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<List<Reclamo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkResponse<List<Reclamo>> listNetworkResponse) {
                        String message = listNetworkResponse.getMessage();
                        if (listNetworkResponse.getStatus() == HTTPConstants.STATUS_OK) {
                            callback.onSuccess(listNetworkResponse.getResponse());
                            Log.i(TAG, "getReclamos, onSuccess: " + message);
                        } else {
                            callback.onError(message);
                            Log.e(TAG, "getReclamos, onSuccess: " + message);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = e.getMessage();
                        callback.onError(message);
                        Log.e(TAG, "getReclamos, onError: " + message);
                    }
                });
    }

    @Override
    public void insertReclamo(Reclamo reclamo, final BaseCallback<String> callback) {
        networkServiceImp
                .insertReclamo(reclamo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkResponse networkResponse) {
                        String message = networkResponse.getMessage();
                        if (networkResponse.getStatus() == HTTPConstants.STATUS_OK) {
                            callback.onSuccess(message);
                            Log.i(TAG, "insertReclamo, onSuccess");
                        } else {
                            callback.onError(message);
                            Log.e(TAG, "insertReclamo, onSuccess: " + message);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = e.getMessage();
                        callback.onError(message);
                        Log.e(TAG, "insertReclamo, onError: " + message);
                    }
                });
    }

    @Override
    public void uploadPhoto(Reclamo reclamo, BaseCallback<String> baseCallback) {
        FTPManager ftpManager = new FTPManager();
        ftpManager
                .uploadFile(reclamo.getImagen(), FTP_ANDROID_IMAGE_DIRECTORY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        insertReclamo(reclamo, baseCallback);
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseCallback.onError(UPLOADING_PHOTO_ERROR);
                        Log.e(TAG, "uploadPhoto, onError: ", e);
                    }
                });
    }
}

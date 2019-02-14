package com.example.ndiaz.parquesbsas.interactor;

import android.util.Log;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.constants.HTTPConstants;
import com.example.ndiaz.parquesbsas.contract.AgregarReclamoContract;
import com.example.ndiaz.parquesbsas.helpers.UploadImageManager;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Reclamo;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
                        addDisposable(d);
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
    public void uploadPhoto(Reclamo reclamo, String imageFilePath, BaseCallback<String> baseCallback) {
        UploadImageManager uploadImageManager =
                new UploadImageManager(ParquesApplication.getInstance().getApplicationContext(), imageFilePath, reclamo.getImagen());
        networkServiceImp
                .uploadFotoReclamo(uploadImageManager.getDescription(), uploadImageManager.getMultipartBody())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkResponse<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(NetworkResponse<String> response) {
                        if (response.getStatus() == HTTPConstants.STATUS_OK) {
                            insertReclamo(reclamo, baseCallback);
                        } else {
                            onError(new Throwable(response.message));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseCallback.onError(UPLOADING_PHOTO_ERROR);
                        Log.e(TAG, "uploadPhoto, onError: ", e);
                    }
                });
    }
}

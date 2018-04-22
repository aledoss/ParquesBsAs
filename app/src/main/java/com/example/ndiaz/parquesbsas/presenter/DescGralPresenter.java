package com.example.ndiaz.parquesbsas.presenter;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.SingleCallback;
import com.example.ndiaz.parquesbsas.contract.DescGralContract;
import com.example.ndiaz.parquesbsas.model.parquelike.ParqueLike;

public class DescGralPresenter extends BasePresenterImp implements DescGralContract.Presenter {

    private static final String TAG = DescGralPresenter.class.getSimpleName();
    private DescGralContract.View view;
    private DescGralContract.Interactor interactor;

    public DescGralPresenter(DescGralContract.View view, DescGralContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void doUpdateParqueLike(final int idParque, final int idUsuario, boolean increase, ParqueLike parqueLike) {
        interactor.updateParqueLike(idParque, idUsuario, increase, parqueLike,
                new BaseCallback<String>() {
                    @Override
                    public void onSuccess(String message) {
                        doGetParqueLikeStatus(idParque, idUsuario);
                        view.enableLikeButtons();
                        Log.i(TAG, "onSuccess: " + message);
                    }

                    @Override
                    public void onError(String message) {
                        view.showMessage(message);
                        view.hideProgressDialog();
                    }
                });
    }

    @Override
    public void doGetParqueLikeStatus(int idParque, int idUsuario) {
        interactor.getParqueLikeStatus(idParque, idUsuario, new SingleCallback<String>() {
            @Override
            public void onSuccess(String parqueLikestatus) {    //idParque, idUsuario, increase (0/1), ej: "4,2,1"
                if (parqueLikestatus.isEmpty()) {
                    view.createDefaultParqueLike();
                } else if (getIncreaseStatus(parqueLikestatus) == 1) {
                    view.createIncreaseParqueLike();
                } else {
                    view.createDecreaseParqueLike();
                }

                view.refreshLikeViews();
            }
        });
    }

    private int getIncreaseStatus(String parqueLikeStatus) {
        String[] parqueLikestatusDivided = parqueLikeStatus.split(",");

        return Integer.parseInt(parqueLikestatusDivided[2]);
    }


}

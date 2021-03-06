package com.example.ndiaz.parquesbsas.contract.basecontract;

import android.view.View;

public interface BaseView {
    void setTransparentStatusBar();

    void setFullscreenMode();

    void showMessage(View view, String message);

    void showProgressDialog();

    void hideProgressDialog();

    void showEmptyAdapter(View llEmptyAdapter, View viewToHide);

    void hideKeyboard();
}

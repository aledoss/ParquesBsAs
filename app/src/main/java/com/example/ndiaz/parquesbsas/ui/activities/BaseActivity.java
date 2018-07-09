package com.example.ndiaz.parquesbsas.ui.activities;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.helpers.permissions.PermissionsManager;
import com.example.ndiaz.parquesbsas.interactor.RXDBInteractor;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;
import com.example.ndiaz.parquesbsas.preferences.PreferencesRepository;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected T presenter;
    protected PreferencesRepository defaultPreferencesRepository;
    protected NetworkServiceImp networkServiceImp;
    protected RXDBInteractor rxdbInteractor;
    private ProgressDialog progressDialog;
    private PermissionsManager permissionsManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkServiceImp = ParquesApplication.getInstance().getNetworkServiceImp();
        rxdbInteractor = ParquesApplication.getInstance().getRxdbInteractor();
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        presenter = createPresenter();
    }

    @Override
    public void setTransparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void setFullscreenMode() {

    }

    protected abstract T createPresenter();

    public PreferencesRepository getDefaultPreferencesRepository() {
        if (defaultPreferencesRepository == null) {
            defaultPreferencesRepository = PreferencesRepository.getDefaultSharedPref(this);
        }
        return defaultPreferencesRepository;
    }

    public NetworkServiceImp getNetworkServiceImp() {
        return networkServiceImp;
    }

    public RXDBInteractor getRxdbInteractor() {
        return rxdbInteractor;
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(View view, String message) {
        Snackbar.make(view, message, BaseTransientBottomBar.LENGTH_LONG).show();
    }

    @Override
    public void showEmptyAdapter(View llEmptyAdapter, View viewToHide) {
        if (llEmptyAdapter.getVisibility() == View.GONE) {
            llEmptyAdapter.setVisibility(View.VISIBLE);
            viewToHide.setVisibility(View.GONE);
        }
    }

    public PermissionsManager getPermissionsManager() {
        if (permissionsManager == null) {
            permissionsManager = new PermissionsManager(this);
        }

        return permissionsManager;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public Usuario getUsuario() {
        return ParquesApplication.getInstance().getUser();
    }

    @Override
    protected void onDestroy() {
        //presenter.onDestroy();
        super.onDestroy();
    }
}

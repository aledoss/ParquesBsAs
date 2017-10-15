package com.example.ndiaz.parquesbsas.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;
import com.example.ndiaz.parquesbsas.network.RetrofitService;
import com.example.ndiaz.parquesbsas.preferences.PreferencesRepository;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected T presenter;
    protected PreferencesRepository defaultPreferencesRepository;
    protected NetworkServiceImp networkServiceImp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDefaultSettings();
        defaultPreferencesRepository = PreferencesRepository.getDefaultSharedPref(this);
        networkServiceImp = new NetworkServiceImp(new RetrofitService());
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

    private void setupDefaultSettings() {
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
    }

    protected abstract T createPresenter();

    @Override
    public void showMessage(View view, String message) {
        Snackbar.make(view, message, BaseTransientBottomBar.LENGTH_LONG).show();
    }
}

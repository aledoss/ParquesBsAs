package com.example.ndiaz.parquesbsas.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.BaseView;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDefaultSettings();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
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

}

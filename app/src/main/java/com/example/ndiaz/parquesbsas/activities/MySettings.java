package com.example.ndiaz.parquesbsas.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.fragments.SettingsFragment;
import com.example.ndiaz.parquesbsas.util.Constants;

public class MySettings extends AppCompatActivity implements Constants {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        setupToolbar();
        setupSettings();
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            boolean recordar = sharedPrefs.getBoolean("settings_checkbox_inicio_sesion_auto", true);
            Log.d("SETTINGS", "Cercania: " + recordar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupSettings() {
        getFragmentManager().beginTransaction().replace(R.id.frameLayout_Settings, new SettingsFragment()).commit();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

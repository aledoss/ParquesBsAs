package com.example.ndiaz.parquesbsas.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.ndiaz.parquesbsas.R;

/**
 * Created by Lenwe on 18/10/2016.
 */

public class SettingsFragment extends PreferenceFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}

package com.example.ndiaz.parquesbsas.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesRepository {

    private SharedPreferences pref;

    public PreferencesRepository(SharedPreferences pref) {
        this.pref = pref;
    }

    public static PreferencesRepository getDefaultSharedPref(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        PreferencesRepository preferenceOptionRepository = new PreferencesRepository(sharedPreferences);
        preferenceOptionRepository.setSharedPrefs(sharedPreferences);

        return preferenceOptionRepository;
    }

    public void setSharedPrefs(SharedPreferences sharedPrefs) {
        this.pref = sharedPrefs;
    }

}

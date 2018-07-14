package com.example.ndiaz.parquesbsas.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DefaultPreferencesRepository {

    private final static String USER_EMAIL = "USER_EMAIL";
    private final static String USER_PASSWORD = "USER_PASSWORD";
    private SharedPreferences pref;

    private DefaultPreferencesRepository(SharedPreferences pref) {
        this.pref = pref;
    }

    public static DefaultPreferencesRepository getDefaultSharedPref(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        DefaultPreferencesRepository preferenceOptionRepository = new DefaultPreferencesRepository(sharedPreferences);
        preferenceOptionRepository.setSharedPrefs(sharedPreferences);

        return preferenceOptionRepository;
    }

    private void setSharedPrefs(SharedPreferences sharedPrefs) {
        this.pref = sharedPrefs;
    }

    public String getUserEmail() {
        return pref.getString(USER_EMAIL, "");
    }

    public String getUserPassword() {
        return pref.getString(USER_PASSWORD, "");
    }

    public boolean getIsAutoLoginEnabled() {
        return pref.getBoolean("settings_checkbox_inicio_sesion_auto", true);
    }

    public void setUserEmail(String email) {
        pref.edit()
                .putString(USER_EMAIL, email)
                .apply();
    }

    public void setUserPassword(String password) {
        pref.edit()
                .putString(USER_PASSWORD, password)
                .apply();
    }

}

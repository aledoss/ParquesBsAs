package com.example.ndiaz.parquesbsas.helpers.permissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;

public class PermissionsManager {

    private BaseActivity baseActivity;
    public final static int ACCESS_FINE_LOCATION_REQUEST_CODE = 1;

    public PermissionsManager(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public void askForLocationPermission(BaseActivity baseActivity) {
        ActivityCompat.requestPermissions(baseActivity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                ACCESS_FINE_LOCATION_REQUEST_CODE);
    }

    public boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(baseActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

}

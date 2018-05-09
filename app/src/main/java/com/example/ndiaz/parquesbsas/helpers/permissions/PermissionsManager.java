package com.example.ndiaz.parquesbsas.helpers.permissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;

public class PermissionsManager {

    private BaseActivity baseActivity;
    public final static int ACCESS_FINE_LOCATION_REQUEST_CODE = 1;
    public final static int ACCESS_CAMERA_REQUEST_CODE = 2;
    public final static int ACCESS_STORAGE_REQUEST_CODE = 3;

    public PermissionsManager(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public void askForLocationPermission(BaseActivity baseActivity) {
        if (!hasLocationPermission()) {
            ActivityCompat.requestPermissions(baseActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_REQUEST_CODE);
        }
    }

    public void askForCameraPermission(BaseActivity baseActivity) {
        if (!hasCameraPermission()) {
            ActivityCompat.requestPermissions(baseActivity,
                    new String[]{Manifest.permission.CAMERA},
                    ACCESS_CAMERA_REQUEST_CODE);
        }
    }

    public void askForStoragePermission(BaseActivity baseActivity) {
        if (!hasStoragePermission()) {
            ActivityCompat.requestPermissions(baseActivity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    ACCESS_CAMERA_REQUEST_CODE);
        }
    }

    public boolean hasLocationPermission() {
        return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public boolean hasCameraPermission() {
        return hasPermission(Manifest.permission.CAMERA);
    }

    public boolean hasStoragePermission() {
        return hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(baseActivity, permission)
                == PackageManager.PERMISSION_GRANTED;
    }
}

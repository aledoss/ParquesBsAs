package com.example.ndiaz.parquesbsas.helpers.maps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

/**
 * https://guides.codepath.com/android/Retrieving-Location-with-LocationServices-API
 */
public class ActualLocation {

    private static final long UPDATE_INTERVAL = 10 * 1200;
    private Context context;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest mLocationRequest;
    private Location lastLocation;
    private LocationCallback locationCallback;
    private LocationSettingsRequest locationSettingsRequest;
    private SettingsClient settingsClient;

    public ActualLocation(Context context) {
        this.context = context;
        configureLocationUpdates();
    }

    private void configureLocationUpdates() {
        createLocationRequest();
        createLocationCallback();
        createLocationSettingsRequest();

        locationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        settingsClient = LocationServices.getSettingsClient(context);
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
    }

    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                onLocationChanged(locationResult.getLastLocation());
            }
        };
    }

    private void createLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        locationSettingsRequest = builder.build();
    }

    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        settingsClient.checkLocationSettings(locationSettingsRequest);

        locationProviderClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());

    }

    public void stopLocationUpdates() {
        locationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void onLocationChanged(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public Location getActualLocation() {
        return this.lastLocation;
    }

}

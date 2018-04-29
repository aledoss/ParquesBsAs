package com.example.ndiaz.parquesbsas.helpers.maps;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ndiaz.parquesbsas.listeners.GoogleLocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class ActualLocation implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Context context;
    private GoogleLocationListener googleLocationListener;

    public ActualLocation(Context context, GoogleLocationListener googleLocationListener) {
        this.context = context;
        this.googleLocationListener = googleLocationListener;
        this.configureConnection();
    }

    private void configureConnection() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //https://www.journaldev.com/13347/android-location-google-play-services
        //http://droidmentor.com/get-the-current-location-in-android/
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleLocationListener.onConnectionSuspended();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        googleLocationListener.onConnectionFailed(connectionResult);
    }

    public void connect() {
        mGoogleApiClient.connect();
    }

    public void disconnect() {
        mGoogleApiClient.disconnect();
    }
}

package com.example.ndiaz.parquesbsas.listeners;

import com.google.android.gms.common.ConnectionResult;

public interface GoogleLocationListener {

    void onConnectionSuspended();

    void onConnectionFailed(ConnectionResult connectionResult);

}

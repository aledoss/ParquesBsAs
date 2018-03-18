package com.example.ndiaz.parquesbsas.helpers.maps;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.ndiaz.parquesbsas.callbacks.PackageCallback;

public class IntentMap {

    private Context context;
    private PackageCallback.Maps callback;

    public IntentMap(Context context, PackageCallback.Maps callback) {
        this.context = context;
        this.callback = callback;
    }

    public void navigateToMapsWithMarker(String latMarker, String lngMarker, String titleMarker) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + latMarker + "," + lngMarker + "(" + titleMarker + ")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (hasMapsInstalled(mapIntent)) {
            context.startActivity(mapIntent);
        } else {
            callback.onMapsPackageNotFound();
        }
    }

    public void navigateToMapsWithTravellInstructions() {
        //https://developers.google.com/maps/documentation/urls/android-intents
    }

    private boolean hasMapsInstalled(Intent mapIntent) {
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            return true;
        } else {
            return false;
        }
    }

}

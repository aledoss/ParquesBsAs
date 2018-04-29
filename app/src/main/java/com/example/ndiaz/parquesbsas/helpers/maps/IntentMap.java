package com.example.ndiaz.parquesbsas.helpers.maps;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.R;

/**
 * //https://developers.google.com/maps/documentation/urls/android-intents
 */
public class IntentMap {

    private Context context;

    public IntentMap(Context context) {
        this.context = context;
    }

    public void navigateToMapsWithMarker(String latMarker, String lngMarker, String titleMarker) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + latMarker + "," + lngMarker + "(" + titleMarker + ")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void navigateToMapsWithTravellInstructions(String latDestination, String lngDestination) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latDestination + "," + lngDestination);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private boolean hasMapsInstalled(Intent mapIntent) {
        return mapIntent.resolveActivity(context.getPackageManager()) != null;
    }

    private void startActivity(Intent mapIntent) {
        if (hasMapsInstalled(mapIntent)) {
            context.startActivity(mapIntent);
        } else {
            Toast.makeText(context, context.getString(R.string.error_maps_package_not_found), Toast.LENGTH_SHORT).show();
        }
    }

}

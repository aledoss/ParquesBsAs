package com.example.ndiaz.parquesbsas.helpers;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.helpers.maps.IntentMap;

public class AlertDialogBuilder {

    /**
     * Builds a dialog. When confirms, it opens maps with navigation travel instructions.
     *
     * @param context
     * @return The dialog was built
     */
    public AlertDialog.Builder buildConfirmationDialogToNavigateMapsWI(Context context, IntentMap intentMap,
                                                                       String latitud, String longitud) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(R.string.showing_maps_route_confirmation)
                .setPositiveButton(context.getString(R.string.dialog_ok), (dialog, which) -> intentMap.navigateToMapsWithTravellInstructions(latitud, longitud))
                .setNegativeButton(context.getString(R.string.dialog_cancel), (dialog, which) -> dialog.dismiss());

        return builder;
    }

}

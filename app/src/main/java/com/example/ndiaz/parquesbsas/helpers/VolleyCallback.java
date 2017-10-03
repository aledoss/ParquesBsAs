package com.example.ndiaz.parquesbsas.helpers;

import org.json.JSONArray;

/**
 * Created by Lenwe on 04/12/2016.
 */

public interface VolleyCallback {

    void onSuccessResponse(JSONArray result);

    void onErrorResponse(String result);
}

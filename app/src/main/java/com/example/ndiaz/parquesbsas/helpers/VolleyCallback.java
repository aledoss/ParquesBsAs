package com.example.ndiaz.parquesbsas.helpers;

import org.json.JSONArray;

public interface VolleyCallback {

    void onSuccessResponse(JSONArray result);

    void onErrorResponse(String result);
}

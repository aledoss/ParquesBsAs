package com.example.ndiaz.parquesbsas.helpers.login_google;

import com.example.ndiaz.parquesbsas.model.Usuario;

public interface OnUserLoggedWithGoogleListener {

    void onLoginWithGoogleSuccess(Usuario usuario);

    void onLoginWithGoogleError(String message);

}

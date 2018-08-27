package com.example.ndiaz.parquesbsas.helpers.login_google;

import com.example.ndiaz.parquesbsas.model.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface LoginWithGoogleListener {

    void doLogin(GoogleSignInAccount account, OnUserLoggedWithGoogleListener listener);

    void doVinculate(GoogleSignInAccount account, Integer idUsuario, OnUserLoggedWithGoogleListener listener);

    void updateUserData(Usuario usuario);

    void onStop();

}

package com.example.ndiaz.parquesbsas;

import android.annotation.SuppressLint;
import android.app.Application;
import android.preference.PreferenceManager;

import com.example.ndiaz.parquesbsas.constants.PassConstants;
import com.example.ndiaz.parquesbsas.interactor.RXDBInteractor;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;
import com.example.ndiaz.parquesbsas.network.RetrofitService;
import com.facebook.stetho.Stetho;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import io.reactivex.Completable;

public class ParquesApplication extends Application {

    private static ParquesApplication instance;
    private Usuario user;
    private Parque parque;
    private NetworkServiceImp networkServiceImp;
    private RXDBInteractor rxdbInteractor;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient mGoogleSignInClient;
    private boolean loggedWithGoogle;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initializeVariables();
        setupDefaultSettings();
    }

    private void initializeVariables() {
        networkServiceImp = new NetworkServiceImp(new RetrofitService());
        rxdbInteractor = new RXDBInteractor(this);
        loggedWithGoogle = false;
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
//            StrictMode.enableDefaults();
        }
    }

    public static ParquesApplication getInstance() {
        return instance;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Parque getParque() {
        return parque;
    }

    public void setParque(Parque parque) {
        this.parque = parque;
    }

    public NetworkServiceImp getNetworkServiceImp() {
        return networkServiceImp;
    }

    public RXDBInteractor getRxdbInteractor() {
        return rxdbInteractor;
    }

    @SuppressLint("CheckResult")
    private void setupDefaultSettings() {
        Completable.fromAction(() -> PreferenceManager.setDefaultValues(ParquesApplication.this, R.xml.settings, false));
    }

    public void clearUser() {
        this.user = null;
    }

    public void setLoggedWithGoogle(boolean loggedWithGoogle) {
        this.loggedWithGoogle = loggedWithGoogle;
    }

    public boolean isLoggedWithGoogle() {
        return loggedWithGoogle;
    }

    public GoogleSignInClient getGoogleSignInClient() {
        if (googleSignInOptions == null || mGoogleSignInClient == null) {
            googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestIdToken(PassConstants.googleClientId)
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        }
        return mGoogleSignInClient;
    }
}

package com.example.ndiaz.parquesbsas.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.SplashScreenContract;
import com.example.ndiaz.parquesbsas.helpers.CipherWrapper;
import com.example.ndiaz.parquesbsas.interactor.SplashScreenInteractor;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.presenter.SplashScreenPresenter;
import com.example.ndiaz.parquesbsas.repositories.UserDataRepository;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class SplashScreenActivity extends BaseActivity<SplashScreenContract.Presenter>
        implements SplashScreenContract.View {

    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    private static final int DELAY_TIME = 5000;
    private static final int MINIMUN_LIMIT_TIME = 3800;
    private static long millisUntilFinished = 5000;
    private CountDownTimer countDownTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        initializeAutoLogin();
        initializeCountDownTimer();
    }

    private void initializeAutoLogin() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            doLoginWithGoogle(account);
        } else {
            presenter.doAutoLogin();
        }
    }

    private void doLoginWithGoogle(GoogleSignInAccount account) {
        Usuario usuario = new Usuario();
        usuario.setGoogleId(account.getId());
        usuario.setEmail(account.getEmail());
        usuario.setNombre(account.getGivenName());
        usuario.setApellido(account.getFamilyName());
        presenter.doLoginWithGoogle(usuario);
    }

    private void initializeCountDownTimer() {
        countDownTimer = new CountDownTimer(DELAY_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                SplashScreenActivity.millisUntilFinished = millisUntilFinished;
                Log.i(TAG, "onTick: " + SplashScreenActivity.millisUntilFinished);
            }

            @Override
            public void onFinish() {
                navigateToLogin();
            }
        }.start();
    }

    @Override
    protected SplashScreenContract.Presenter createPresenter() {
        SplashScreenContract.Interactor interactor = new SplashScreenInteractor(
                getDefaultDefaultPreferencesRepository(), getNetworkServiceImp(),
                new UserDataRepository(getDefaultDefaultPreferencesRepository(), new CipherWrapper()));

        return new SplashScreenPresenter(this, interactor);
    }

    @Override
    public void navigateToHome() {
        Log.i(TAG, "navigateToHome, user logged ");
        if (millisUntilFinished > MINIMUN_LIMIT_TIME) {
            navigateWithDelay(HomeActivity.class);
        } else {
            navigate(HomeActivity.class);
        }
    }

    @Override
    public void navigateToLogin() {
        Log.i(TAG, "navigateToHome, user unlogged ");
        if (millisUntilFinished > MINIMUN_LIMIT_TIME) {
            navigateWithDelay(LoginActivity.class);
        } else {
            navigate(LoginActivity.class);
        }
    }

    private void navigate(Class<?> activityTo) {
        countDownTimer.cancel();
        startActivity(new Intent(SplashScreenActivity.this, activityTo)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    private void navigateWithDelay(Class<?> activityTo) {
        countDownTimer.cancel();
        new Handler().postDelayed(() -> startActivity(new Intent(SplashScreenActivity.this, activityTo)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)), 1500);
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
        finish();
    }

}

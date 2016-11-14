package com.example.ndiaz.parquesbsas.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.database.DBHelper;
import com.example.ndiaz.parquesbsas.database.Usuario;
import com.example.ndiaz.parquesbsas.util.Constants;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Constants {

    private Button btnIniciarSesion, btnCrearCuenta, btnRecuperarContraseña;
    private EditText etEmail, etPassword;
    private LinearLayout linearLayout;
    private String email, password;
    private boolean recordarDatosLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDefaultSettings();
        verificarLogin();   //se fija si el usuario decidio guardar sus datos y que se loguee automaticamente
        setContentView(R.layout.activity_main);
        transparentStatusBar();
        setupUI();
    }

    private void verificarLogin() {
        try {
            recordarDatosLogin = obtenerDatosSharedPreferencesSettings();
            if (recordarDatosLogin) {
                obtenerDatosLoginSharedPreferences();
                Usuario usuario = obtenerUsuario();
                if (usuario != null) {
                    iniciarSesion(usuario, false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void obtenerDatosLoginSharedPreferences() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(LOGINPREFERENCES, Context.MODE_PRIVATE);
            email = sharedPreferences.getString(EMAILLOGINSAVED, "");
            password = sharedPreferences.getString(PASSWORDLOGINSAVED, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean obtenerDatosSharedPreferencesSettings() {
        //obtengo datos del settings
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        recordarDatosLogin = sharedPrefs.getBoolean(SETTINGS_CHECBOX_INICIO_SESION_AUTO, true);
        Log.d("SETTINGS", "Recordar: " + recordarDatosLogin);
        return recordarDatosLogin;
    }

    private void setupUI() {
        btnIniciarSesion = (Button) findViewById(R.id.btnIniciar_Sesion);
        btnCrearCuenta = (Button) findViewById(R.id.btnCrear_Cuenta_Login);
        btnRecuperarContraseña = (Button) findViewById(R.id.btnRecuperarContraseña);
        etEmail = (EditText) findViewById(R.id.etEmailLogin);
        etPassword = (EditText) findViewById(R.id.etPasswordLogin);
        linearLayout = (LinearLayout) findViewById(R.id.activity_main_layout);
        mostrarOcultarPass();

        btnIniciarSesion.setOnClickListener(this);
        btnCrearCuenta.setOnClickListener(this);
        btnRecuperarContraseña.setOnClickListener(this);
    }

    private void mostrarOcultarPass() {
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!etPassword.getText().toString().equalsIgnoreCase("")) {
                    final int DRAWABLE_RIGHT = 2;
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                            return true;
                        }
                    } else {
                        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                    }
                }
                return false;
            }
        });
    }

    private void transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIniciar_Sesion:
                obtenerDatosLogin();
                if (datosNoVacios()) {
                    Usuario usuario = obtenerUsuario();
                    if (usuario != null) {
                        iniciarSesion(usuario, true);
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.DatosIncorrectos), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getResources().getString(R.string.DatosVacios), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnCrear_Cuenta_Login:
                startActivity(new Intent(MainActivity.this, CrearCuenta.class));
                break;
            case R.id.btnRecuperarContraseña:
                Snackbar.make(linearLayout, getResources().getString(R.string.WorkInProgress), Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    private Usuario obtenerUsuario() {
        DBHelper db = new DBHelper(this);
        Usuario usuario = db.getUsuario(email, password);
        db.close();
        if (usuario != null) {
            return usuario;
        }
        return null;
    }

    private boolean datosNoVacios() {
        if (email.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }

    private void iniciarSesion(Usuario usuario, boolean guardarDatos) {
        if (guardarDatos) {
            try {
                SharedPreferences sharedPreferences = getSharedPreferences(LOGINPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(EMAILLOGINSAVED, email);
                editor.putString(PASSWORDLOGINSAVED, password);
                editor.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(MainActivity.this, MainHome.class);
        intent.putExtra(INICIARSESIONUSUARIO, (Serializable) usuario);
        startActivity(intent);
        finish();
    }

    private void obtenerDatosLogin() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
    }

    private void setupDefaultSettings() {
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
    }


}
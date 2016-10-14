package com.example.ndiaz.parquesbsas.activities.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.activities.util.Constants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Constants {

    private Button btnIniciarSesion, btnCrearCuenta, btnRecuperarContraseña;
    private EditText etEmail, etPassword;
    private LinearLayout linearLayout;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transparentStatusBar();
        setupUI();
    }

    private void setupUI() {
        btnIniciarSesion = (Button) findViewById(R.id.btnIniciar_Sesion);
        btnCrearCuenta = (Button) findViewById(R.id.btnCrear_Cuenta_Login);
        btnRecuperarContraseña = (Button) findViewById(R.id.btnRecuperarContraseña);
        etEmail = (EditText) findViewById(R.id.etEmailLogin);
        etPassword = (EditText) findViewById(R.id.etPasswordLogin);
        linearLayout = (LinearLayout) findViewById(R.id.activity_main_layout);

        btnIniciarSesion.setOnClickListener(this);
        btnCrearCuenta.setOnClickListener(this);
        btnRecuperarContraseña.setOnClickListener(this);
    }

    private void transparentStatusBar() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIniciar_Sesion:
                obtenerDatosLogin();
                if(datosNoVacios()){
                    iniciarSesion();
                }else{
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

    private boolean datosNoVacios() {
        if (email.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }

    private void iniciarSesion() {
        Intent intent = new Intent(MainActivity.this, MainHome.class);
        intent.putExtra(LOGINEMAIL, email);
        intent.putExtra(LOGINPASSWORD, password);
        startActivity(intent);
    }

    private void obtenerDatosLogin() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
    }
}

package com.example.ndiaz.parquesbsas.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.activities.util.Constants;

public class CrearCuenta extends AppCompatActivity implements View.OnClickListener, Constants {

    private Button btnCrearCuenta;
    private EditText etNombre, etApellido, etDNI, etEmail, etPassword;
    private String nombre, apellido, dni, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        transparentStatusBar();
        setupUI();

    }

    private void setupUI() {
        btnCrearCuenta = (Button) findViewById(R.id.btnCrear_Cuenta);
        etNombre = (EditText) findViewById(R.id.etNombreCrearCuenta);
        etApellido = (EditText) findViewById(R.id.etApellidoCrearCuenta);
        etDNI = (EditText) findViewById(R.id.etDNICrearCuenta);
        etEmail = (EditText) findViewById(R.id.etEmailCrearCuenta);
        etPassword = (EditText) findViewById(R.id.etPasswordCrearCuenta);

        btnCrearCuenta.setOnClickListener(this);
    }

    private void transparentStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCrear_Cuenta:
                obtenerDatosCrearCuenta();
                if (datosNoVacios()) {
                    crearCuenta();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.DatosVacios), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void crearCuenta() {
        Intent intent = new Intent(CrearCuenta.this, MainHome.class);
        intent.putExtra(CREARCUENTANOMBRE, nombre);
        intent.putExtra(CREARCUENTAAPELLIDO, apellido);
        if (!dni.equalsIgnoreCase("")) {
            intent.putExtra(CREARCUENTADNI, dni);
        } else {
            intent.putExtra(CREARCUENTADNI, "");
        }
        intent.putExtra(CREARCUENTAEMAIL, email);
        intent.putExtra(CREARCUENTAPASSWORD, password);
        startActivity(intent);
    }

    private boolean datosNoVacios() {
        if (nombre.equalsIgnoreCase("") || apellido.equalsIgnoreCase("") ||
                email.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }

    private void obtenerDatosCrearCuenta() {
        nombre = etNombre.getText().toString();
        apellido = etApellido.getText().toString();
        dni = etDNI.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
    }
}

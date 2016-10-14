package com.example.ndiaz.parquesbsas.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.database.DBHelper;
import com.example.ndiaz.parquesbsas.database.Usuario;
import com.example.ndiaz.parquesbsas.util.Constants;

import java.io.Serializable;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCrear_Cuenta:
                obtenerDatosCrearCuenta();
                if (datosNoVacios()) {
                    if (cuentaDuplicada()) {
                        Toast.makeText(this, getResources().getString(R.string.EmailExistente), Toast.LENGTH_SHORT).show();
                    } else {
                        Usuario usuario = crearCuenta();
                        accederHome(usuario);
                    }
                } else {
                    Toast.makeText(this, getResources().getString(R.string.DatosVacios), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void accederHome(Usuario usuario) {
        Intent intent = new Intent(CrearCuenta.this, MainHome.class);
        intent.putExtra(CREARCUENTAUSUARIO, (Serializable) usuario);
        startActivity(intent);
    }

    private boolean cuentaDuplicada() {
        boolean cuentaDuplicada;
        DBHelper db = new DBHelper(this);
        Usuario usuario = db.getUsuario(email, password);
        if (usuario == null) {
            db.close();
            cuentaDuplicada = false;
        } else {
            db.close();
            cuentaDuplicada = true;
        }
        return cuentaDuplicada;
    }

    private Usuario crearCuenta() {
        DBHelper db = new DBHelper(this);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDni(dni);
        usuario.setEmail(email);
        usuario.setPassword(password);

        db.insertarUsuario(usuario);
        db.close();

        return usuario;
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

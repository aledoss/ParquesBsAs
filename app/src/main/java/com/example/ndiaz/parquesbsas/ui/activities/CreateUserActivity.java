package com.example.ndiaz.parquesbsas.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.constants.Constants;
import com.example.ndiaz.parquesbsas.contract.CreateUserContract;
import com.example.ndiaz.parquesbsas.database.DBHelper;
import com.example.ndiaz.parquesbsas.model.Usuario;

import java.io.Serializable;

import static com.example.ndiaz.parquesbsas.constants.LoginConstants.EMAILLOGINSAVED;
import static com.example.ndiaz.parquesbsas.constants.LoginConstants.LOGINPREFERENCES;
import static com.example.ndiaz.parquesbsas.constants.LoginConstants.PASSWORDLOGINSAVED;

public class CreateUserActivity extends BaseActivity<CreateUserContract.Presenter>
        implements View.OnClickListener, Constants {

    private Button btnCrearCuenta;
    private EditText etNombre, etApellido, etDNI, etEmail, etPassword;
    private String nombre, apellido, dni, email, password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        setTransparentStatusBar();
        setupUI();
    }

    @Override
    protected CreateUserContract.Presenter createPresenter() {
        // TODO: 16/10/2017 Agregar el new del Presenter
        return null;
    }

    //Butterknife
    private void setupUI() {
        btnCrearCuenta = (Button) findViewById(R.id.btnCrear_Cuenta);
        etNombre = (EditText) findViewById(R.id.etNombreCrearCuenta);
        etApellido = (EditText) findViewById(R.id.etApellidoCrearCuenta);
        etDNI = (EditText) findViewById(R.id.etDNICrearCuenta);
        etEmail = (EditText) findViewById(R.id.etEmailCrearCuenta);
        etPassword = (EditText) findViewById(R.id.etPasswordCrearCuenta);
        mostrarOcultarPass();

        btnCrearCuenta.setOnClickListener(this);
    }

    //Butterknife
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

    //Butterknife
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

    //Funcionalidad de autologin + acceder al home
    private void accederHome(Usuario usuario) {
        SharedPreferences sharedPreferences = getSharedPreferences(LOGINPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAILLOGINSAVED, email);
        editor.putString(PASSWORDLOGINSAVED, password);
        editor.commit();
        Intent intent = new Intent(CreateUserActivity.this, MainHome.class);
        intent.putExtra(CREARCUENTAUSUARIO, (Serializable) usuario);
        startActivity(intent);
        finish();
    }

    //Esto vendría del servidor
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

    // Lo maneja el servidor
    private Usuario crearCuenta() {
        DBHelper db = new DBHelper(this);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setNumeroDoc(dni);
        usuario.setEmail(email);
        usuario.setPassword(password);

        db.insertarUsuario(usuario);
        db.close();

        return usuario;
    }

    //Validar con los EditTextValidator
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

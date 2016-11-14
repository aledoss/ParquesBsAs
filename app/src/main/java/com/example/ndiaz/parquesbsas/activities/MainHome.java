package com.example.ndiaz.parquesbsas.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.database.DBHelper;
import com.example.ndiaz.parquesbsas.database.Parque;
import com.example.ndiaz.parquesbsas.database.Usuario;
import com.example.ndiaz.parquesbsas.util.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, Constants {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toogle;
    private NavigationView navigationView;
    private GoogleMap googleMap;
    private Usuario usuario;
    private TextView txtNombre, txtEmail;
    private ImageView imgPerfilUsuario;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        verificarDatosParque();
        usuario = obtenerDatosUsuario();
        setupUI();

    }

    private void verificarDatosParque() {
        if(ingresoPrimeraVez()){//me fijo si es la primera vez que ingresa
            guardarDatosParquesBD();
        }
    }

    private void guardarDatosParquesBD() {

    }

    private boolean ingresoPrimeraVez() {
        SharedPreferences mPrefs = getSharedPreferences(INGRESOPRIMERAVEZ, Context.MODE_PRIVATE);
        boolean primeraVez = mPrefs.getBoolean(INGRESOPRIMERAVEZ, true);
        if(primeraVez){
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(INGRESOPRIMERAVEZ, false);
            editor.commit();
            return true;
        }
        return false;
    }

    private Usuario obtenerDatosUsuario() {
        try {
            usuario = (Usuario) getIntent().getExtras().getSerializable(INICIARSESIONUSUARIO);
            if (usuario == null) {
                usuario = (Usuario) getIntent().getExtras().getSerializable(CREARCUENTAUSUARIO);
            }
            return usuario;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setupUI() {
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout_home);
        setupToolbar();
        setupDrawerLayout();
        setupNavigationHeader();
        setupMap();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
    }

    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                syncState();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
    }

    private void setupNavigationHeader() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        imgPerfilUsuario = (ImageView) header.findViewById(R.id.nav_drawer_profile_image);
        txtNombre = (TextView) header.findViewById(R.id.nav_drawer_txt_name);
        txtEmail = (TextView) header.findViewById(R.id.nav_drawer_txt_email);

        if (usuario != null) {
            txtNombre.setText(usuario.getNombre() + " " + usuario.getApellido());
            txtEmail.setText(usuario.getEmail());
        }
    }

    private void setupMap() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa_home);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toogle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toogle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toogle.onOptionsItemSelected(item)) {
            return true;
        }
        //int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_menu_parques:
                startActivity(new Intent(MainHome.this, ListaParques.class));
                break;
            case R.id.nav_menu_perfil:
                mostrarSnackbar();
                break;
            case R.id.nav_menu_reclamos:
                mostrarSnackbar();
                break;
            case R.id.nav_menu_settings:
                startActivity(new Intent(MainHome.this, MySettings.class));
                break;
            case R.id.nav_menu_about_me:
                mostrarDialogAboutMe();
                break;
            case R.id.nav_menu_logout:
                logout();
                break;
        }

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        return false;
    }

    private void mostrarDialogAboutMe() {
        AlertDialog dialog = new AlertDialog.Builder(MainHome.this).create();
        dialog.setTitle(getResources().getString(R.string.menu_about_me));
        dialog.setMessage(getResources().getString(R.string.about_me_text));
        dialog.show();
    }

    private void mostrarSnackbar() {
        Snackbar.make(linearLayout, getResources().getString(R.string.WorkInProgress), Snackbar.LENGTH_SHORT).show();
    }

    private void logout() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(LOGINPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(EMAILLOGINSAVED, "");
            editor.putString(PASSWORDLOGINSAVED, "");
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
        startActivity(new Intent(MainHome.this, MainActivity.class));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng capitalFederal = new LatLng(-34.612892, -58.4707548);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(capitalFederal, 11));
        try {
            DBHelper db = new DBHelper(MainHome.this);
            ArrayList<Parque> listaParques = db.getAllParques();
            LatLng parqueLatLng;
            for (Parque parque : listaParques) {
                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 65, 65, false);
                parqueLatLng = new LatLng(Double.parseDouble(parque.getLatitud()), Double.parseDouble(parque.getLongitud()));
                googleMap.addMarker(new MarkerOptions()
                        .title(parque.getNombre())
                        .snippet(parque.getDescripcionCorta())
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                        .position(parqueLatLng)
                );
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}

package com.example.ndiaz.parquesbsas.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.HomeContract;
import com.example.ndiaz.parquesbsas.interactor.HomeInteractor;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.presenter.HomePresenter;
import com.example.ndiaz.parquesbsas.ui.activities.info_parques.ParqueActivity;
import com.example.ndiaz.parquesbsas.ui.activities.reclamos.ListaReclamosUsuarioActivity;
import com.example.ndiaz.parquesbsas.ui.dialogs.RateItDialogFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.BindView;

import static com.example.ndiaz.parquesbsas.constants.Constants.PARQUEDETALLES;

public class HomeActivity extends BaseActivity<HomeContract.Presenter> implements HomeContract.View,
        NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @BindView(R.id.toolbar_home)
    Toolbar toolbar;
    @BindView(R.id.main_activity_drawer_layout)
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @BindView(R.id.linear_layout_home)
    LinearLayout lLContainer;

    private TextView txtNombre, txtEmail;
    private ImageView imgPerfilUsuario;
    private ActionBarDrawerToggle toogle;
    private GoogleMap googleMap;
    private Usuario usuario;
    private boolean canLoadParques;
    private List<Parque> parques;
    private Menu navMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //usuario = obtenerDatosUsuario();
        setupUI();
        initializeVariables();
        initializeViews();
        presenter.doGetParques();
    }

    @Override
    protected HomeContract.Presenter createPresenter() {
        HomeContract.Interactor homeInteractor = new HomeInteractor(getNetworkServiceImp(),
                getRxdbInteractor());

        return new HomePresenter(this, homeInteractor);
    }

    private void initializeViews() {
        RateItDialogFragment.show(this, getSupportFragmentManager());
        validateViewsFromProfile();
    }

    private void validateViewsFromProfile() {
        if (getUsuario() == null) {
            navMenu.findItem(R.id.nav_menu_perfil).setVisible(false);
            navMenu.findItem(R.id.nav_menu_reclamos).setVisible(false);
            navMenu.findItem(R.id.nav_menu_logout).setVisible(false);
        }
    }

    private void initializeVariables() {
        canLoadParques = false;
    }

    private void setupUI() {
        setupToolbar();
        setupDrawerLayout();
        setupNavigationView();
        setupMap();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
    }

    private void setupDrawerLayout() {
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

    private void setupNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navMenu = navigationView.getMenu();
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_menu_parques:
                startActivity(new Intent(HomeActivity.this, ListaParquesActivity.class));
                break;
            case R.id.nav_menu_perfil:
                startActivity(new Intent(HomeActivity.this, PerfilUsuarioActivity.class));
                break;
            case R.id.nav_menu_reclamos:
                startActivity(new Intent(HomeActivity.this, ListaReclamosUsuarioActivity.class));
                break;
            case R.id.nav_menu_settings:
                startActivity(new Intent(HomeActivity.this, MySettings.class));
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
        AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this).create();
        dialog.setTitle(getResources().getString(R.string.menu_about_me));
        dialog.setMessage(getResources().getString(R.string.about_me_text));
        dialog.show();
    }

    private void mostrarSnackbar() {
        Snackbar.make(lLContainer, getResources().getString(R.string.WorkInProgress), Snackbar.LENGTH_SHORT).show();
    }

    private void logout() {
        /*try {
            SharedPreferences sharedPreferences = getSharedPreferences(LOGINPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(EMAILLOGINSAVED, "");
            editor.putString(PASSWORDLOGINSAVED, "");
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        finish();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        canLoadParques = true;
//        LatLng capitalFederal = new LatLng(-34.612892, -58.4707548);
        LatLng capitalFederal = new LatLng(-34.6182053, -58.4386018);
        this.googleMap = googleMap;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(capitalFederal, 11.5f));
        if (parques != null) {
            loadParques(parques);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 14.5f));
        presenter.doGetParqueFromNetw((int) marker.getZIndex());
        return true;
    }

    @Override
    public void loadParques(List<Parque> parques) {
        if (canLoadParques) {
            LatLng parqueLatLng;
            for (Parque parque : parques) {
                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 65, 65, false);
                parqueLatLng = new LatLng(Double.parseDouble(parque.getLatitud()), Double.parseDouble(parque.getLongitud()));
                googleMap.addMarker(new MarkerOptions()
                        //.title(parque.getNombre())
                        //.snippet(parque.getDescripcionCorta())
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                        .position(parqueLatLng)
                        .zIndex(parque.getIdParque())
                );
            }
            googleMap.setOnMarkerClickListener(this);
            hideProgressDialog();
        }
        this.parques = parques;
    }

    @Override
    public void showParquesDialog(final Parque parque) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle(parque.getNombre())
                .setMessage(getResources().getString(R.string.mas_informacion))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(HomeActivity.this, ParqueActivity.class);
                        intent.putExtra(PARQUEDETALLES, parque);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create().show();
    }

    @Override
    public void showMessage(String message) {
        showMessage(lLContainer, message);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

package com.example.ndiaz.parquesbsas.ui.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.cluster.ParqueCluster;
import com.example.ndiaz.parquesbsas.cluster.ParqueClusterRendered;
import com.example.ndiaz.parquesbsas.contract.HomeContract;
import com.example.ndiaz.parquesbsas.interactor.HomeInteractor;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.presenter.HomePresenter;
import com.example.ndiaz.parquesbsas.ui.activities.info_parques.ParqueActivity;
import com.example.ndiaz.parquesbsas.ui.activities.reclamos.ListaReclamosUsuarioActivity;
import com.example.ndiaz.parquesbsas.ui.dialogs.RateItDialogFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;

import butterknife.BindView;

import static com.example.ndiaz.parquesbsas.constants.Constants.PARQUEDETALLES;

public class HomeActivity extends BaseActivity<HomeContract.Presenter> implements HomeContract.View,
        NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        ClusterManager.OnClusterItemClickListener<ParqueCluster>, ClusterManager.OnClusterClickListener<ParqueCluster> {

    @BindView(R.id.toolbar_home)
    Toolbar toolbar;
    @BindView(R.id.main_activity_drawer_layout)
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @BindView(R.id.linear_layout_home)
    LinearLayout lLContainer;

    private TextView txtWelcome;
    private ActionBarDrawerToggle toogle;
    private GoogleMap googleMap;
    private boolean canLoadParques;
    private List<Parque> parques;
    private Menu navMenu;
    private ClusterManager<ParqueCluster> clusterManager;
    private ParqueClusterRendered clusterRendered;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
            navMenu.findItem(R.id.nav_menu_login).setVisible(true);
        }
    }

    private void initializeVariables() {
        canLoadParques = false;
    }

    private void setupUI() {
        setupToolbar();
        setupDrawerLayout();
        setupNavigationView();
        initializeMap();
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
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navMenu = navigationView.getMenu();
        View header = navigationView.getHeaderView(0);
        String saludo = getString(R.string.welcome);

        txtWelcome = header.findViewById(R.id.nav_drawer_txt_welcome);

        if (getUsuario() != null) {
            saludo = saludo + " " + getUsuario().getNombre();
        }
        txtWelcome.setText(saludo);
    }

    private void initializeMap() {
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
            case R.id.nav_menu_login:
                navigateToLogin();
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

    private void navigateToLogin() {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        canLoadParques = true;
//        LatLng capitalFederal = new LatLng(-34.612892, -58.4707548);
        LatLng capitalFederal = new LatLng(-34.6182053, -58.4386018);
        this.googleMap = googleMap;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(capitalFederal, 11.5f));
        clusterManager = new ClusterManager<>(this, googleMap);
        clusterRendered = new ParqueClusterRendered(this, googleMap, clusterManager);
        clusterManager.setOnClusterItemClickListener(this);
        clusterManager.setOnClusterClickListener(this);
        googleMap.setOnCameraIdleListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);
        if (parques != null) {
            loadParquesInTheMap(parques);
        }
    }

    @Override
    public void loadParquesInTheMap(List<Parque> parques) {
        if (canLoadParques) {
            for (Parque parque : parques) {
                clusterManager.addItem(new ParqueCluster(parque));
            }
            clusterManager.setRenderer(clusterRendered);
            clusterManager.cluster();
            hideProgressDialog();
        }
        this.parques = parques;
    }

    @Override
    public boolean onClusterItemClick(ParqueCluster parqueCluster) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(parqueCluster.getPosition(), 14.5f));
        presenter.doGetParqueFromNetw(parqueCluster.getParque().getIdParque());
        return true;
    }

    @Override
    public boolean onClusterClick(Cluster<ParqueCluster> cluster) {
        float newZoom = googleMap.getCameraPosition().zoom + 1;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cluster.getPosition(), newZoom));
        return true;
    }

    @Override
    public void navigateToParque(Parque parque) {
        startActivity(new Intent(HomeActivity.this, ParqueActivity.class)
                .putExtra(PARQUEDETALLES, parque));
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

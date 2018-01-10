package com.example.ndiaz.parquesbsas.ui.activities.componentes_parque;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.ListaActividadesContract;
import com.example.ndiaz.parquesbsas.helpers.RecyclerItemClickListener;
import com.example.ndiaz.parquesbsas.interactor.ListaActividadesInteractor;
import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.presenter.ListaActividadesPresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;
import com.example.ndiaz.parquesbsas.ui.adapters.ActividadesAdapter;

import java.util.List;

import butterknife.BindView;

public class ListaActividadesActivity extends BaseActivity<ListaActividadesContract.Presenter> implements
        ListaActividadesContract.View {

    public static final String ACTIVIDAD = "ACTIVIDAD";
    @BindView(R.id.lLContainer)
    LinearLayout llContainer;
    @BindView(R.id.rvActividadesParque)
    RecyclerView rvActividadesParque;
    @BindView(R.id.toolbar_lista_actividades_parque)
    Toolbar toolbar;

    private ActividadesAdapter adapter;
    private int idParque;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actividades_parque);
        initializeVariables();
        setupToolbar();
        setupAdapter();
        presenter.doGetActividades(idParque);
    }

    private void initializeVariables() {
        this.idParque = ParquesApplication.getInstance().getParque().getIdParque();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.actividades);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupAdapter() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvActividadesParque.setLayoutManager(mLayoutManager);
        adapter = new ActividadesAdapter();
        rvActividadesParque.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                navigateToActividadHorarios(adapter.getItemList().get(position));
            }
        }));
        rvActividadesParque.setAdapter(adapter);
    }

    private void navigateToActividadHorarios(Actividad actividad) {
        Intent intent = new Intent(ListaActividadesActivity.this, ListaActividadesHorariosActivity.class);
        intent.putExtra(ACTIVIDAD, actividad);
        startActivity(intent);
    }

    @Override
    protected ListaActividadesContract.Presenter createPresenter() {
        ListaActividadesInteractor interactor = new ListaActividadesInteractor(getNetworkServiceImp());

        return new ListaActividadesPresenter(this, interactor);
    }

    @Override
    public void showActividades(List<Actividad> actividades) {
        adapter.setItemList(actividades);
    }

    @Override
    public void showMessage(String message) {
        showMessage(llContainer, message);
    }

}

package com.example.ndiaz.parquesbsas.ui.activities.componentes_parque;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.ListaActividadesHorariosContract;
import com.example.ndiaz.parquesbsas.interactor.ListaActividadesHorariosInteractor;
import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.presenter.ListaActividadesHorariosPresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;
import com.example.ndiaz.parquesbsas.ui.adapters.ActividadesHorariosAdapter;

import java.util.List;

import butterknife.BindView;

import static com.example.ndiaz.parquesbsas.ui.activities.componentes_parque.ListaActividadesActivity.ACTIVIDAD;

public class ListaActividadesHorariosActivity extends BaseActivity<ListaActividadesHorariosContract.Presenter>
        implements ListaActividadesHorariosContract.View {

    @BindView(R.id.lLContainer)
    LinearLayout llContainer;
    @BindView(R.id.toolbar_lista_actividades_horarios)
    Toolbar toolbar;
    @BindView(R.id.rvActividadesHorariosParque)
    RecyclerView rvActividadesHorariosParque;

    private Parque parque;
    private Actividad actividad;
    private ActividadesHorariosAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actividades_horarios);
        initializeVariables();
        setupToolbar();
        setupAdapter();
        presenter.doGetHorarios(parque.getIdParque(), actividad.getId());
    }

    @Override
    protected ListaActividadesHorariosContract.Presenter createPresenter() {
        ListaActividadesHorariosInteractor interactor = new ListaActividadesHorariosInteractor(getNetworkServiceImp());

        return new ListaActividadesHorariosPresenter(this, interactor);
    }

    private void initializeVariables() {
        this.parque = ParquesApplication.getInstance().getParque();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            actividad = bundle.getParcelable(ACTIVIDAD);
        }
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(actividad.getNombre() + " - " + getString(R.string.actividades_horarios));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupAdapter() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvActividadesHorariosParque.setLayoutManager(mLayoutManager);
        adapter = new ActividadesHorariosAdapter();
        rvActividadesHorariosParque.setAdapter(adapter);
    }

    @Override
    public void showHorarios(List<Actividad> actividades) {
        adapter.setItemList(actividades);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        showMessage(llContainer, message);
    }

}

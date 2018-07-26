package com.example.ndiaz.parquesbsas.ui.activities.info_parques;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.FiltroParqueContract;
import com.example.ndiaz.parquesbsas.helpers.recyclerview.RecyclerSpaceItemDecoration;
import com.example.ndiaz.parquesbsas.interactor.FiltroParqueInteractor;
import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.model.Feria;
import com.example.ndiaz.parquesbsas.model.filter_checkbox.ActividadesFilterCheckbox;
import com.example.ndiaz.parquesbsas.model.filter_checkbox.FeriasFilterCheckbox;
import com.example.ndiaz.parquesbsas.presenter.FiltroParquePresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;
import com.example.ndiaz.parquesbsas.ui.adapters.FilterCheckBoxAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FiltroParqueActivity extends BaseActivity<FiltroParquePresenter>
        implements FiltroParqueContract.View {

    @BindView(R.id.container)
    ConstraintLayout container;
    @BindView(R.id.rvActividadesContainer)
    RecyclerView rvActividadesContainer;
    @BindView(R.id.rvFeriasContainer)
    RecyclerView rvFeriasContainer;
    @BindView(R.id.toolbar_filtro_parque)
    Toolbar toolbar;

    private FilterCheckBoxAdapter actividadesAdapter;
    private FilterCheckBoxAdapter feriasAdapter;
    private int cantColumns;
    private int spacing;

    @OnClick(R.id.fabFiltrar)
    public void onClickFiltrar() {
        // TODO: 18/07/2018 obtener datos y crear objeto ParqueFilter
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_parque);
        initializeVariables();

        presenter.doGetActividades();
        presenter.doGetFerias();
        initializeToolbar();
    }

    private void initializeVariables() {
        cantColumns = getResources().getInteger(R.integer.filter_checkbox_grid_columns);
        spacing = (int) getResources().getDimension(R.dimen.filter_checkbox_grid_space);
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.filters);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected FiltroParquePresenter createPresenter() {
        FiltroParqueInteractor interactor = new FiltroParqueInteractor(getNetworkServiceImp());

        return new FiltroParquePresenter(this, interactor);
    }

    @Override
    public void showMessage(String message) {
        showMessage(container, message);
    }

    @Override
    public void showActividades(List<Actividad> actividades) {
        addActividadesToAdapter(actividades);
    }

    private void addActividadesToAdapter(List<Actividad> actividades) {
        if (actividadesAdapter == null) {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, cantColumns);
            rvActividadesContainer.setLayoutManager(mLayoutManager);
            rvActividadesContainer.addItemDecoration(new RecyclerSpaceItemDecoration(spacing));
            actividadesAdapter = new FilterCheckBoxAdapter(new ActividadesFilterCheckbox(actividades));
        }
        rvActividadesContainer.setAdapter(actividadesAdapter);
    }

    @Override
    public void showFerias(List<Feria> ferias) {
        addFeriasToAdapter(ferias);
    }

    private void addFeriasToAdapter(List<Feria> ferias) {
        if (feriasAdapter == null) {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, cantColumns);
            rvFeriasContainer.addItemDecoration(new RecyclerSpaceItemDecoration(spacing));
            rvFeriasContainer.setLayoutManager(mLayoutManager);
            feriasAdapter = new FilterCheckBoxAdapter(new FeriasFilterCheckbox(ferias));
        }
        rvFeriasContainer.setAdapter(feriasAdapter);
    }
}

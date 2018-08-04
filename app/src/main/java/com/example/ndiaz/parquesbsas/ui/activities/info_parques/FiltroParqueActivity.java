package com.example.ndiaz.parquesbsas.ui.activities.info_parques;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.FiltroParqueContract;
import com.example.ndiaz.parquesbsas.helpers.recyclerview.RecyclerSpaceItemDecoration;
import com.example.ndiaz.parquesbsas.interactor.FiltroParqueInteractor;
import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.model.Feria;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.ParqueFilter;
import com.example.ndiaz.parquesbsas.model.filter_checkbox.FilterCheckboxDataHelper;
import com.example.ndiaz.parquesbsas.presenter.FiltroParquePresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;
import com.example.ndiaz.parquesbsas.ui.adapters.FilterCheckBoxAdapter;
import com.example.ndiaz.parquesbsas.ui.custom.filter_checkbox.FilterCheckBox;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

public class FiltroParqueActivity extends BaseActivity<FiltroParquePresenter>
        implements FiltroParqueContract.View {

    public static final String PARQUES_FROM_FILTRO = "PARQUES_FROM_FILTRO";
    @BindView(R.id.container)
    ConstraintLayout container;
    @BindView(R.id.rvActividadesContainer)
    RecyclerView rvActividadesContainer;
    @BindView(R.id.rvFeriasContainer)
    RecyclerView rvFeriasContainer;
    @BindView(R.id.toolbar_filtro_parque)
    Toolbar toolbar;
    @BindView(R.id.fChkBoxItinerantes)
    FilterCheckBox fChkBoxItinerantes;
    @BindView(R.id.fChkBoxEstSalud)
    FilterCheckBox fChkBoxEstSalud;
    @BindView(R.id.fChkBoxPatioJuegos)
    FilterCheckBox fChkBoxPatioJuegos;

    private FilterCheckBoxAdapter actividadesAdapter;
    private FilterCheckBoxAdapter feriasAdapter;
    private FilterCheckboxDataHelper filterCheckboxDataHelper;
    private int cantColumns;
    private int spacing;

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
        filterCheckboxDataHelper = new FilterCheckboxDataHelper();
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
            actividadesAdapter = new FilterCheckBoxAdapter(filterCheckboxDataHelper.createActividadesFilterCheckBox(actividades));
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
            feriasAdapter = new FilterCheckBoxAdapter(filterCheckboxDataHelper.createFeriasFilterCheckbox(ferias));
        }
        rvFeriasContainer.setAdapter(feriasAdapter);
    }

    @Override
    public void showListParquesActivity(List<Parque> parques) {
        Intent intent = new Intent().putExtra(PARQUES_FROM_FILTRO, (Serializable) parques);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filtro_parque_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_aplicar_filtro) {
            filterParques();
        }

        return super.onOptionsItemSelected(item);
    }

    private void filterParques() {
        List<Actividad> actividadesMarcadas = filterCheckboxDataHelper.getActividadesMarcadas(actividadesAdapter.getItems());
        List<Feria> feriasMarcadas = filterCheckboxDataHelper.getFeriasMarcadas(feriasAdapter.getItems());
        boolean feriaItineranteSelected = fChkBoxItinerantes.isChecked();
        boolean centroSaludSelected = fChkBoxEstSalud.isChecked();
        boolean patioJuegosSelected = fChkBoxPatioJuegos.isChecked();

        ParqueFilter parqueFilter = new ParqueFilter(actividadesMarcadas, feriasMarcadas,
                feriaItineranteSelected, centroSaludSelected, patioJuegosSelected);
        presenter.doFilterParques(parqueFilter);
        Log.i("NICOTEST", "onClickFiltrar: actividades: " + actividadesMarcadas.size() + ", ferias: " + feriasMarcadas.size());
    }
}

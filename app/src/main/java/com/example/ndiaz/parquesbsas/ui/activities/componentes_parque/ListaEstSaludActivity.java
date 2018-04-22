package com.example.ndiaz.parquesbsas.ui.activities.componentes_parque;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.ListaEstSaludContract;
import com.example.ndiaz.parquesbsas.interactor.ListaEstSaludInteractor;
import com.example.ndiaz.parquesbsas.model.EstacionSaludable;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.presenter.ListaEstSaludPresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;
import com.example.ndiaz.parquesbsas.ui.adapters.EstSaludAdapter;

import java.util.List;

import butterknife.BindView;

public class ListaEstSaludActivity extends BaseActivity<ListaEstSaludContract.Presenter>
        implements ListaEstSaludContract.View{

    @BindView(R.id.toolbar_lista_est_salud)
    Toolbar toolbar;
    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    @BindView(R.id.rvEstSaludParque)
    RecyclerView rvEstSaludParque;

    private Parque parque;
    private EstSaludAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_est_salud);
        initializeVariables();
        setupToolbar();
        initializeAdapter();
        presenter.doGetEstSalud(parque.getIdParque());
    }

    private void initializeVariables() {
        this.parque = ParquesApplication.getInstance().getParque();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.estaciones_saludables);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeAdapter() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvEstSaludParque.setLayoutManager(mLayoutManager);
        adapter = new EstSaludAdapter(this, parque);
        rvEstSaludParque.setAdapter(adapter);
    }

    @Override
    protected ListaEstSaludContract.Presenter createPresenter() {
        ListaEstSaludInteractor interactor = new ListaEstSaludInteractor(getNetworkServiceImp());

        return new ListaEstSaludPresenter(this, interactor);
    }

    @Override
    public void showEstSalud(List<EstacionSaludable> estSaludables) {
        adapter.setItemList(estSaludables);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        showMessage(llContainer, message);
    }
}

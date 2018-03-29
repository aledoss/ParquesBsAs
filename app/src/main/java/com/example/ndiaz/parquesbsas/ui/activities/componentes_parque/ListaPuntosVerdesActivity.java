package com.example.ndiaz.parquesbsas.ui.activities.componentes_parque;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.ListaPuntosVerdesContract;
import com.example.ndiaz.parquesbsas.interactor.ListaPuntosVerdesInteractor;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.PuntoVerde;
import com.example.ndiaz.parquesbsas.presenter.ListaPuntosVerdesPresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;
import com.example.ndiaz.parquesbsas.ui.adapters.PuntosVerdesAdapter;

import java.util.List;

import butterknife.BindView;

public class ListaPuntosVerdesActivity extends BaseActivity<ListaPuntosVerdesContract.Presenter>
        implements ListaPuntosVerdesContract.View {

    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    @BindView(R.id.toolbar_lista_puntos_verdes)
    Toolbar toolbar;
    @BindView(R.id.rvPuntosVerdesParque)
    RecyclerView rvPuntosVerdesParque;

    private Parque parque;
    private PuntosVerdesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_puntos_verdes);
        initializeVariables();
        setupToolbar();
        initializeAdapter();
        presenter.doGetPuntosVerdes(parque.getIdParque());
    }

    @Override
    protected ListaPuntosVerdesContract.Presenter createPresenter() {
        ListaPuntosVerdesInteractor interactor = new ListaPuntosVerdesInteractor(getNetworkServiceImp());

        return new ListaPuntosVerdesPresenter(this, interactor);
    }

    private void initializeVariables() {
        this.parque = ParquesApplication.getInstance().getParque();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.puntos_verdes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeAdapter() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvPuntosVerdesParque.setLayoutManager(mLayoutManager);
        adapter = new PuntosVerdesAdapter(parque);
        rvPuntosVerdesParque.setAdapter(adapter);
    }

    @Override
    public void showPuntosVerdes(List<PuntoVerde> puntosVerdes) {
        adapter.setItemList(puntosVerdes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        showMessage(llContainer, message);
    }
}

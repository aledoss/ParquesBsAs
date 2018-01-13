package com.example.ndiaz.parquesbsas.ui.activities.componentes_parque;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.ListaFeriasContract;
import com.example.ndiaz.parquesbsas.interactor.ListaFeriasInteractor;
import com.example.ndiaz.parquesbsas.model.Feria;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.presenter.ListaFeriasPresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;
import com.example.ndiaz.parquesbsas.ui.adapters.FeriasAdapter;

import java.util.List;

import butterknife.BindView;

public class ListaFeriasActivity extends BaseActivity<ListaFeriasContract.Presenter>
        implements ListaFeriasContract.View {

    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    @BindView(R.id.toolbar_lista_ferias)
    Toolbar toolbar;
    @BindView(R.id.rvFeriasParque)
    RecyclerView rvFeriasParque;

    private Parque parque;
    private FeriasAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ferias);
        initializeVariables();
        setupToolbar();
        initializeAdapter();
        presenter.doGetFerias(parque.getIdParque());
    }

    @Override
    protected ListaFeriasContract.Presenter createPresenter() {
        ListaFeriasInteractor interactor = new ListaFeriasInteractor(getNetworkServiceImp());

        return new ListaFeriasPresenter(this, interactor);
    }

    private void initializeVariables() {
        this.parque = ParquesApplication.getInstance().getParque();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.ferias);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeAdapter() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvFeriasParque.setLayoutManager(mLayoutManager);
        adapter = new FeriasAdapter(parque);
        rvFeriasParque.setAdapter(adapter);
    }

    @Override
    public void showFerias(List<Feria> ferias) {
        adapter.setItemList(ferias);
    }

    @Override
    public void showMessage(String message) {
        showMessage(llContainer, message);
    }
}

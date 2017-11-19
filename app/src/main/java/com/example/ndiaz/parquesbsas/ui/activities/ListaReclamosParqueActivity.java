package com.example.ndiaz.parquesbsas.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.ListaReclamosParqueContract;
import com.example.ndiaz.parquesbsas.interactor.ListaReclamosParqueInteractor;
import com.example.ndiaz.parquesbsas.model.Reclamo;
import com.example.ndiaz.parquesbsas.presenter.ListaReclamosParquePresenter;
import com.example.ndiaz.parquesbsas.ui.activities.reclamos.AgregarReclamoActivity;
import com.example.ndiaz.parquesbsas.ui.adapters.ReclamosParqueAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.ndiaz.parquesbsas.constants.Constants.ID_PARQUE;

public class ListaReclamosParqueActivity extends BaseActivity<ListaReclamosParqueContract.Presenter>
        implements ListaReclamosParqueContract.View {

    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    @BindView(R.id.emptyContainer)
    LinearLayout emptyContainer;
    @BindView(R.id.rvReclamosParque)
    RecyclerView rvReclamosParque;
    @BindView(R.id.toolbar_lista_reclamos_parque)
    Toolbar toolbar;

    private ReclamosParqueAdapter adapter;
    private int idParque;

    @OnClick(R.id.btnAgregarReclamo)
    void btnAgregarReclamo() {
        startActivity(new Intent(ListaReclamosParqueActivity.this, AgregarReclamoActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reclamos_parque);
        assignBundleVariables();
        setupToolbar();
        presenter.doGetReclamos(1);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.lista_reclamos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void assignBundleVariables() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idParque = bundle.getInt(ID_PARQUE, 0);
        }
    }

    @Override
    protected ListaReclamosParqueContract.Presenter createPresenter() {
        ListaReclamosParqueContract.Interactor interactor =
                new ListaReclamosParqueInteractor(getNetworkServiceImp());

        return new ListaReclamosParquePresenter(this, interactor);
    }

    @Override
    public void showReclamos(List<Reclamo> reclamos) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        rvReclamosParque.setLayoutManager(mLayoutManager);
        adapter = new ReclamosParqueAdapter(reclamos);
        rvReclamosParque.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        showMessage(llContainer, message);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showEmptyContainer() {
        emptyContainer.setVisibility(View.VISIBLE);
        rvReclamosParque.setVisibility(View.GONE);
    }
}

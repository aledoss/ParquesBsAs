package com.example.ndiaz.parquesbsas.ui.activities.reclamos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.ListaReclamosParqueContract;
import com.example.ndiaz.parquesbsas.interactor.ListaReclamosParqueInteractor;
import com.example.ndiaz.parquesbsas.model.Reclamo;
import com.example.ndiaz.parquesbsas.presenter.ListaReclamosParquePresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;
import com.example.ndiaz.parquesbsas.ui.adapters.ReclamosParqueAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.ndiaz.parquesbsas.constants.Constants.ID_PARQUE;
import static com.example.ndiaz.parquesbsas.constants.Constants.MESSAGE;

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
    @BindView(R.id.btnAgregarReclamo)
    FloatingActionButton fabAgregarReclamo;

    private ReclamosParqueAdapter adapter;
    private int idParque;

    @OnClick(R.id.btnAgregarReclamo)
    void btnAgregarReclamo() {
        if (getUsuario().hasDocument()) {
            startActivityForResult(new Intent(ListaReclamosParqueActivity.this, AgregarReclamoActivity.class),
                    AgregarReclamoActivity.RESULT_CODE_RECLAMO);
        } else {
            showSinDocumentoMessage();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reclamos_parque);
        obtenerDatos();
        setupToolbar();
        initializeViews();
        presenter.doGetReclamos(idParque);
    }

    private void initializeViews() {
        if (getUsuario() != null) {
            fabAgregarReclamo.setVisibility(View.VISIBLE);
        }
        initializeReclamosAdapter();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.lista_reclamos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void obtenerDatos() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idParque = bundle.getInt(ID_PARQUE, idParque);
        }
    }

    @Override
    protected ListaReclamosParqueContract.Presenter createPresenter() {
        ListaReclamosParqueContract.Interactor interactor =
                new ListaReclamosParqueInteractor(getNetworkServiceImp());

        return new ListaReclamosParquePresenter(this, interactor);
    }

    public void initializeReclamosAdapter() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvReclamosParque.setLayoutManager(mLayoutManager);
        adapter = new ReclamosParqueAdapter();
        rvReclamosParque.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        showMessage(llContainer, message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AgregarReclamoActivity.RESULT_CODE_RECLAMO) {
            if (data != null) {
                showMessage(data.getStringExtra(MESSAGE));
                presenter.doGetReclamos(idParque);
            }
        } else if (requestCode == AgregarReclamoActivity.RESULT_CODE_SIN_DOCUMENTO) {
            showSinDocumentoMessage();
        }
    }

    private void showSinDocumentoMessage() {
        showMessage(getString(R.string.reclamo_sin_documento));
    }

    @Override
    public void showEmptyContainer() {
        emptyContainer.setVisibility(View.VISIBLE);
        rvReclamosParque.setVisibility(View.GONE);
    }

    @Override
    public void refreshReclamos(List<Reclamo> reclamos) {
        if (reclamos != null & !reclamos.isEmpty()){
            adapter.setItemList(reclamos);
            adapter.notifyDataSetChanged();
            emptyContainer.setVisibility(View.GONE);
            rvReclamosParque.setVisibility(View.VISIBLE);
        }
    }
}

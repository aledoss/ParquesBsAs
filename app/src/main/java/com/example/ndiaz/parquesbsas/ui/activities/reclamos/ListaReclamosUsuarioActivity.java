package com.example.ndiaz.parquesbsas.ui.activities.reclamos;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.ListaReclamosUsuarioContract;
import com.example.ndiaz.parquesbsas.helpers.RecyclerItemClickListener;
import com.example.ndiaz.parquesbsas.interactor.ListaReclamosUsuarioInteractor;
import com.example.ndiaz.parquesbsas.model.ReclamoFecha;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.presenter.ListaReclamosUsuarioPresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;
import com.example.ndiaz.parquesbsas.ui.adapters.ReclamosUsuarioAdapter;
import com.example.ndiaz.parquesbsas.ui.dialogs.ReclamoDialogFragment;

import java.util.List;

import butterknife.BindView;

public class ListaReclamosUsuarioActivity extends BaseActivity<ListaReclamosUsuarioContract.Presenter>
        implements ListaReclamosUsuarioContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar_lista_reclamos_usuario)
    Toolbar toolbar;
    @BindView(R.id.rvReclamosUsuario)
    RecyclerView rvReclamosUsuario;
    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    @BindView(R.id.emptyContainer)
    LinearLayout emptyContainer;
    @BindView(R.id.swipeRefreshReclamosUsuario)
    SwipeRefreshLayout swipeRefresh;

    private Usuario usuario;
    private ReclamosUsuarioAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reclamos_usuario);
        initializeVariables();
        initializeViews();
        setupToolbar();
        presenter.doGetReclamosConFechas(usuario.getId(), false);
    }

    @Override
    protected ListaReclamosUsuarioContract.Presenter createPresenter() {
        ListaReclamosUsuarioInteractor interactor = new ListaReclamosUsuarioInteractor(getNetworkServiceImp());

        return new ListaReclamosUsuarioPresenter(this, interactor);
    }

    private void initializeVariables() {
        this.usuario = ParquesApplication.getInstance().getUser();
    }

    private void initializeViews() {
        swipeRefresh.setOnRefreshListener(this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.lista_mis_reclamos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showReclamosConFechas(List<ReclamoFecha> reclamosFechas) {
        if (adapter == null) {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            rvReclamosUsuario.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            rvReclamosUsuario.setLayoutManager(mLayoutManager);
            rvReclamosUsuario.addOnItemTouchListener(new RecyclerItemClickListener(this, (view, position) -> {
                ReclamoFecha reclamoFecha = adapter.getItem(position);
                if (reclamoFecha.getReclamo() != null) {
                    ReclamoDialogFragment dialog = ReclamoDialogFragment.newInstance(reclamoFecha.getReclamo());
                    dialog.show(getSupportFragmentManager(), ReclamoDialogFragment.class.getSimpleName());
                }
            }));
            adapter = new ReclamosUsuarioAdapter(reclamosFechas);
        }

        rvReclamosUsuario.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        showMessage(llContainer, message);
    }

    @Override
    public void showEmptyContainer() {
        emptyContainer.setVisibility(View.VISIBLE);
        rvReclamosUsuario.setVisibility(View.GONE);
    }

    @Override
    public void refreshReclamos(List<ReclamoFecha> reclamosFechas) {
        adapter.setItemList(reclamosFechas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        presenter.doGetReclamosConFechas(usuario.getId(), true);
    }

    @Override
    public void hideSwipeRefresh() {
        swipeRefresh.setRefreshing(false);
    }
}

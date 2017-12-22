package com.example.ndiaz.parquesbsas.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.ListaParquesContract;
import com.example.ndiaz.parquesbsas.helpers.RecyclerItemClickListener;
import com.example.ndiaz.parquesbsas.interactor.ListaParquesInteractor;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.presenter.ListaParquesPresenter;
import com.example.ndiaz.parquesbsas.ui.activities.info_parques.ParqueActivity;
import com.example.ndiaz.parquesbsas.ui.adapters.ParquesAdapter;

import java.util.List;

import butterknife.BindView;

import static com.example.ndiaz.parquesbsas.constants.Constants.PARQUEDETALLES;

public class ListaParquesActivity extends BaseActivity<ListaParquesContract.Presenter> implements
        ListaParquesContract.View, SearchView.OnQueryTextListener {

    @BindView(R.id.lLContainer)
    LinearLayout lLContainer;
    @BindView(R.id.llEmptyAdapter)
    LinearLayout llEmptyAdapter;
    @BindView(R.id.rvParques)
    RecyclerView rvParques;
    @BindView(R.id.toolbar_lista_parques)
    Toolbar toolbar;

    private ParquesAdapter adapter;
    private List<Parque> parques;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_parques);
        setupToolbar();
        setupAdapter();
        presenter.doGetParques();
    }

    @Override
    protected ListaParquesContract.Presenter createPresenter() {
        ListaParquesInteractor interactor = new ListaParquesInteractor(getRxdbInteractor());

        return new ListaParquesPresenter(this, interactor);
    }

    private void setupAdapter() {
        if (adapter == null) {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            rvParques.setLayoutManager(mLayoutManager);
            adapter = new ParquesAdapter();
            rvParques.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    navigateToParqueDetails(adapter.getItemList().get(position));
                }
            }));
        }

        rvParques.setAdapter(adapter);
    }

    private void navigateToParqueDetails(Parque parque) {
        Intent intent = new Intent(ListaParquesActivity.this, ParqueActivity.class);
        intent.putExtra(PARQUEDETALLES, parque);
        startActivity(intent);
    }

    @Override
    public void showParques(List<Parque> parques, boolean refreshData) {
        if (!refreshData) {
            this.parques = parques;
        }
        handleAdapterVisibility();
        adapter.setItemList(parques);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        showMessage(lLContainer, message);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.lista_parques);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_parques_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_menu);
        setupSearchView(menuItem);

        return super.onCreateOptionsMenu(menu);
    }

    private void setupSearchView(MenuItem menuItem) {
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.nombre_parque));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        for (Parque parque : adapter.getItemList()) {
            if (parque.getNombre().equalsIgnoreCase(query)) {
                navigateToParqueDetails(parque);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.isEmpty()) {
            presenter.doGetParquesFiltered(this.parques, newText);
        } else {
            showParques(this.parques, true);
        }

        return true;
    }

    @Override
    public void showEmptyAdapter() {
        if (llEmptyAdapter.getVisibility() == View.GONE) {
            llEmptyAdapter.setVisibility(View.VISIBLE);
            rvParques.setVisibility(View.GONE);
        }
    }

    private void handleAdapterVisibility() {
        if (rvParques.getVisibility() == View.GONE) {
            rvParques.setVisibility(View.VISIBLE);
            llEmptyAdapter.setVisibility(View.GONE);
        }
    }
}

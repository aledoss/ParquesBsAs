package com.example.ndiaz.parquesbsas.ui.activities.old;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.ListaParquesContract;
import com.example.ndiaz.parquesbsas.helpers.RecyclerItemClickListener;
import com.example.ndiaz.parquesbsas.interactor.ListaParquesInteractor;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.presenter.ListaParquesPresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;
import com.example.ndiaz.parquesbsas.ui.adapters.ParquesAdapter;

import java.util.List;

import butterknife.BindView;

public class ListaParquesActivity extends BaseActivity<ListaParquesContract.Presenter> implements
        ListaParquesContract.View {

    @BindView(R.id.lLContainer)
    LinearLayout lLContainer;
    @BindView(R.id.rvParques)
    RecyclerView rvParques;
    @BindView(R.id.toolbar_lista_parques)
    Toolbar toolbar;

    private ParquesAdapter adapter;

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
                    /*Parque parque = adapter.getParques().get(position);
                    Intent intent = new Intent();
                    intent.putExtra(PARQUEDETALLES, parque);
                    startActivity(intent);*/
                    Toast.makeText(ListaParquesActivity.this, adapter.getParques().get(position).getNombre(), Toast.LENGTH_SHORT).show();
                }
            }));
        }

        rvParques.setAdapter(adapter);
    }

    @Override
    public void showParques(List<Parque> parques) {
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
        return super.onCreateOptionsMenu(menu);
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
}

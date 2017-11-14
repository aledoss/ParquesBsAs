package com.example.ndiaz.parquesbsas.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.ListaReclamosParqueContract;
import com.example.ndiaz.parquesbsas.helpers.RecyclerItemClickListener;
import com.example.ndiaz.parquesbsas.interactor.ListaReclamosParqueInteractor;
import com.example.ndiaz.parquesbsas.model.Reclamo;
import com.example.ndiaz.parquesbsas.presenter.ListaReclamosParquePresenter;
import com.example.ndiaz.parquesbsas.ui.adapters.ReclamosParqueAdapter;

import java.util.List;

import butterknife.BindView;

public class ListaReclamosParqueActivity extends BaseActivity<ListaReclamosParqueContract.Presenter>
        implements ListaReclamosParqueContract.View {

    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    @BindView(R.id.emptyContainer)
    LinearLayout emptyContainer;
    @BindView(R.id.rvReclamosParque)
    RecyclerView rvReclamosParque;

    private ReclamosParqueAdapter adapter;
    private int idParque;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reclamos_parque);
        //obtener del bundle el id del parque
        //presenter.doGetReclamos(idParque);
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
        rvReclamosParque.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Reclamo reclamo = adapter.getItem(position);
                //ir a detalles del reclamo.
            }
        }));

    }

    @Override
    public void showMessage(String message) {
        showMessage(llContainer, message);
    }

    @Override
    public void showEmptyContainer() {
        emptyContainer.setVisibility(View.VISIBLE);
        rvReclamosParque.setVisibility(View.GONE);
    }
}

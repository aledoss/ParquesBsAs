package com.example.ndiaz.parquesbsas.ui.activities.componentes_parque;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.ListaFeriasItinerantesContract;
import com.example.ndiaz.parquesbsas.interactor.ListaFeriasItinerantesInteractor;
import com.example.ndiaz.parquesbsas.model.FeriaItinerante;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.presenter.ListaFeriasItinerantesPresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;
import com.example.ndiaz.parquesbsas.ui.adapters.FeriasItinerantesAdapter;

import java.util.List;

import butterknife.BindView;

public class ListaFeriasItinerantesActivity extends BaseActivity<ListaFeriasItinerantesContract.Presenter>
        implements ListaFeriasItinerantesContract.View {

    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    @BindView(R.id.toolbar_lista_ferias_itinerantes)
    Toolbar toolbar;
    @BindView(R.id.rvFeriasItinerantesParque)
    RecyclerView rvFeriasItinerantesParque;

    private Parque parque;
    private FeriasItinerantesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ferias_itinereantes);
        initializeVariables();
        setupToolbar();
        initializeAdapter();
        presenter.doGetFeriasItinerantes(parque.getIdParque());
    }

    @Override
    protected ListaFeriasItinerantesContract.Presenter createPresenter() {
        ListaFeriasItinerantesInteractor interactor = new ListaFeriasItinerantesInteractor(getNetworkServiceImp());

        return new ListaFeriasItinerantesPresenter(this, interactor);
    }

    private void initializeVariables() {
        this.parque = ParquesApplication.getInstance().getParque();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.ferias_itinerantes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeAdapter() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvFeriasItinerantesParque.setLayoutManager(mLayoutManager);
        adapter = new FeriasItinerantesAdapter(this, parque);
        rvFeriasItinerantesParque.setAdapter(adapter);
    }

    @Override
    public void showFeriasItinerantes(List<FeriaItinerante> ferias) {
        adapter.setItemList(ferias);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        showMessage(llContainer, message);
    }
}

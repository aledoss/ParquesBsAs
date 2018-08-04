package com.example.ndiaz.parquesbsas.ui.activities.componentes_parque;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.ListaEncuestasParqueContract;
import com.example.ndiaz.parquesbsas.helpers.recyclerview.RecyclerItemClickListener;
import com.example.ndiaz.parquesbsas.interactor.ListaEncuestasParqueInteractor;
import com.example.ndiaz.parquesbsas.listeners.OnCalificarEncuestaListener;
import com.example.ndiaz.parquesbsas.model.Calificacion;
import com.example.ndiaz.parquesbsas.model.CalificacionEncuesta;
import com.example.ndiaz.parquesbsas.model.Encuesta;
import com.example.ndiaz.parquesbsas.presenter.ListaEncuestasParquePresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;
import com.example.ndiaz.parquesbsas.ui.adapters.EncuestasParqueAdapter;
import com.example.ndiaz.parquesbsas.ui.dialogs.CalificarEncuestaDialogFragment;
import com.example.ndiaz.parquesbsas.ui.dialogs.EstadisticasEncuestasDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.ndiaz.parquesbsas.constants.Constants.ID_PARQUE;

public class ListaEncuestasParqueActivity extends BaseActivity<ListaEncuestasParqueContract.Presenter>
        implements ListaEncuestasParqueContract.View, OnCalificarEncuestaListener {

    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    @BindView(R.id.emptyContainer)
    LinearLayout emptyContainer;
    @BindView(R.id.rvEncuestasParque)
    RecyclerView rvEncuestasParque;
    @BindView(R.id.btnCalificar)
    FloatingActionButton btnCalificarEncuesta;
    @BindView(R.id.toolbar_lista_encuestas_parque)
    Toolbar toolbar;

    private EncuestasParqueAdapter adapter;
    private List<Encuesta> encuestasParaCalificar;
    private List<Calificacion> calificaciones;
    private CalificarEncuestaDialogFragment calificarEncuestaDialogFragment;
    private int idParque;
    private int idUsuario;

    @OnClick(R.id.btnCalificar)
    void btnCalificarEncuesta() {
        calificarEncuestaDialogFragment = CalificarEncuestaDialogFragment.newInstance(encuestasParaCalificar, calificaciones, this);
        calificarEncuestaDialogFragment.show(getSupportFragmentManager(), CalificarEncuestaDialogFragment.class.getSimpleName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_encuestas_parque);
        assignBundleVariables();
        setupToolbar();
        if (getUsuario() != null){
            idUsuario = getUsuario().getId();
            getEncuestasParaCalificar();
            presenter.doGetCalificaciones();
        }
        getEncuestasByParque(false);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.lista_encuestas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void assignBundleVariables() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idParque = bundle.getInt(ID_PARQUE, idParque);
        }
    }

    @Override
    protected ListaEncuestasParqueContract.Presenter createPresenter() {
        ListaEncuestasParqueContract.Interactor interactor =
                new ListaEncuestasParqueInteractor(getNetworkServiceImp());

        return new ListaEncuestasParquePresenter(this, interactor);
    }

    @Override
    public void showEncuestas(List<Encuesta> encuestas) {
        if (adapter == null) {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            rvEncuestasParque.setLayoutManager(mLayoutManager);
            rvEncuestasParque.addOnItemTouchListener(new RecyclerItemClickListener(this, (view, position) -> {
                presenter.doGetEstadisticasEncuesta(idParque, adapter.getItem(position));
            }));
            adapter = new EncuestasParqueAdapter(encuestas);
        }

        rvEncuestasParque.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        showMessage(llContainer, message);
    }

    @Override
    public void showEmptyContainer() {
        emptyContainer.setVisibility(View.VISIBLE);
        rvEncuestasParque.setVisibility(View.GONE);
    }

    @Override
    public void setEncuestasParaCalificar(List<Encuesta> encuestasParaCalificar) {
        this.encuestasParaCalificar = encuestasParaCalificar;
    }

    @Override
    public void setCalificaciones(List<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }

    @Override
    public void getEncuestasParaCalificar() {
        presenter.doGetEncuestasParaCalificar(idParque, idUsuario);
    }

    @Override
    public void getEncuestasByParque(boolean refreshData) {
        presenter.doGetEncuestasByParque(idParque, refreshData);
    }

    @Override
    public void refreshEncuestas(List<Encuesta> encuestas) {
        adapter.setItemList(encuestas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showEstadisticasEncuesta(Encuesta encuesta, Calificacion calificacion) {
        EstadisticasEncuestasDialogFragment.newInstance(encuesta, calificacion)
                .show(getSupportFragmentManager(), "");
    }

    @Override
    public void updateButtonCalificarEncuestaVisibility() {
        int visibilty = ((this.encuestasParaCalificar != null && !this.encuestasParaCalificar.isEmpty()) &&
                (this.calificaciones != null && !this.calificaciones.isEmpty())) ?
                View.VISIBLE : View.GONE;
        btnCalificarEncuesta.setVisibility(visibilty);
    }

    @Override
    public void onCalificarEncuesta(Encuesta encuesta, Calificacion calificacion) {
        CalificacionEncuesta calificacionEncuesta = new CalificacionEncuesta(calificacion.getIdCalificacion(),
                encuesta.getIdEncuesta(), idParque, idUsuario);
        presenter.doCreateCalificacionEncuesta(calificacionEncuesta);
    }

    @Override
    public void closeCalificarEncuestaDialog() {
        if (calificarEncuestaDialogFragment != null && calificarEncuestaDialogFragment.getDialog().isShowing()) {
            calificarEncuestaDialogFragment.dismiss();
        }
    }

    @Override
    public void showSuccessInsertCalificacionEncuesta(String msg) {
        showMessage(msg);
    }

    @Override
    public void showFailInsertCalificacionEncuesta(String message) {
        calificarEncuestaDialogFragment.onFailInsertCalificacionEncuesta(message);
    }

    @Override
    public void clearEncuestasParaCalificarList() {
        if (encuestasParaCalificar != null) {
            this.encuestasParaCalificar.clear();
        }
    }
}

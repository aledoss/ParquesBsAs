package com.example.ndiaz.parquesbsas.ui.activities.info_parques;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.FiltroParqueContract;
import com.example.ndiaz.parquesbsas.interactor.FiltroParqueInteractor;
import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.model.Feria;
import com.example.ndiaz.parquesbsas.presenter.FiltroParquePresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FiltroParqueActivity extends BaseActivity<FiltroParquePresenter>
        implements FiltroParqueContract.View {

    @BindView(R.id.container)
    ConstraintLayout container;

    @OnClick(R.id.fabFiltrar)
    public void onClickFiltrar() {
        // TODO: 18/07/2018 obtener datos y crear objeto ParqueFilter
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_parque);

        presenter.doGetActividades();
        presenter.doGetFerias();
    }

    @Override
    protected FiltroParquePresenter createPresenter() {
        FiltroParqueInteractor interactor = new FiltroParqueInteractor(getNetworkServiceImp());

        return new FiltroParquePresenter(this, interactor);
    }

    @Override
    public void showMessage(String message) {
        showMessage(container, message);
    }

    @Override
    public void showActividades(List<Actividad> actividades) {
        addActividadesToAdapter(actividades);
    }

    private void addActividadesToAdapter(List<Actividad> actividades) {
        // TODO: 19/07/2018 Mostrar titulo de Actividades junto con la lista
    }

    @Override
    public void showFerias(List<Feria> ferias) {
        addFeriasToAdapter(ferias);
    }

    private void addFeriasToAdapter(List<Feria> ferias) {
        // TODO: 19/07/2018 Mostrar titulo de Ferias junto con la lista
    }
}

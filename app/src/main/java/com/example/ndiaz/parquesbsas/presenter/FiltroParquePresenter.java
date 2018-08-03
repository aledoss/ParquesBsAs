package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.FiltroParqueContract;
import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.model.Feria;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.ParqueFilter;

import java.util.List;

public class FiltroParquePresenter extends BasePresenterImp
        implements FiltroParqueContract.Presenter {

    private FiltroParqueContract.View view;
    private FiltroParqueContract.Interactor interactor;

    public FiltroParquePresenter(FiltroParqueContract.View view, FiltroParqueContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void doGetActividades() {
        interactor.getActividadesToFilter(new BaseCallback<List<Actividad>>() {
            @Override
            public void onSuccess(List<Actividad> actividades) {
                view.showActividades(actividades);
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        });
    }

    @Override
    public void doGetFerias() {
        interactor.getFeriasToFilter(new BaseCallback<List<Feria>>() {
            @Override
            public void onSuccess(List<Feria> ferias) {
                view.showFerias(ferias);
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        });
    }

    @Override
    public void doFilterParques(ParqueFilter filter) {
        interactor.filterParques(filter, new BaseCallback<List<Parque>>() {
            @Override
            public void onSuccess(List<Parque> parques) {
                view.showListParquesActivity(parques);
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        });
    }
}

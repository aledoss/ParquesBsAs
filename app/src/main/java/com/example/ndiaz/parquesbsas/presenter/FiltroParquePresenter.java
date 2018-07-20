package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.EmptyCallback;
import com.example.ndiaz.parquesbsas.contract.FiltroParqueContract;
import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.model.Feria;
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
        interactor.getActividades(new BaseCallback<List<Actividad>>() {
            @Override
            public void onSuccess(List<Actividad> value) {

            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    public void doGetFerias() {
        interactor.getFerias(new BaseCallback<List<Feria>>() {
            @Override
            public void onSuccess(List<Feria> value) {

            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    public void doFilter(ParqueFilter filter) {
        interactor.filter(filter, new EmptyCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(String message) {

            }
        });
    }
}

package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaActividadesHorariosContract;
import com.example.ndiaz.parquesbsas.model.Actividad;

import java.lang.ref.WeakReference;
import java.util.List;

public class ListaActividadesHorariosPresenter extends BasePresenterImp
        implements ListaActividadesHorariosContract.Presenter {

    private WeakReference<ListaActividadesHorariosContract.View> listaActividadesHorariosView;
    private ListaActividadesHorariosContract.Interactor listaActividadesHorariosInteractor;

    public ListaActividadesHorariosPresenter(ListaActividadesHorariosContract.View listaActividadesHorariosView,
                                             ListaActividadesHorariosContract.Interactor listaActividadesHorariosInteractor) {
        this.listaActividadesHorariosView = new WeakReference<>(listaActividadesHorariosView);
        this.listaActividadesHorariosInteractor = listaActividadesHorariosInteractor;
    }


    @Override
    public void doGetHorarios(int idParque, int idActividad) {
        listaActividadesHorariosInteractor.getHorarios(idParque, idActividad,
                new BaseCallback<List<Actividad>>() {
                    @Override
                    public void onSuccess(List<Actividad> actividades) {
                        listaActividadesHorariosView.get().showHorarios(actividades);
                    }

                    @Override
                    public void onError(String message) {
                        listaActividadesHorariosView.get().showMessage(message);
                    }
                });
    }
}

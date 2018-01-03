package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaActividadesContract;
import com.example.ndiaz.parquesbsas.model.Actividad;

import java.lang.ref.WeakReference;
import java.util.List;

public class ListaActividadesPresenter extends BasePresenterImp
        implements ListaActividadesContract.Presenter {

    private WeakReference<ListaActividadesContract.View> listaActividadesView;
    private ListaActividadesContract.Interactor listaActividadesInteractor;

    public ListaActividadesPresenter(ListaActividadesContract.View listaActividadesView,
                                     ListaActividadesContract.Interactor listaActividadesInteractor) {
        this.listaActividadesView = new WeakReference<>(listaActividadesView);
        this.listaActividadesInteractor = listaActividadesInteractor;
    }


    @Override
    public void doGetActividades(int idParque) {
        listaActividadesInteractor.getActividades(idParque, new BaseCallback<List<Actividad>>() {
            @Override
            public void onSuccess(List<Actividad> actividades) {
                listaActividadesView.get().showActividades(actividades);
            }

            @Override
            public void onError(String message) {
                listaActividadesView.get().showMessage(message);
            }
        });
    }
}

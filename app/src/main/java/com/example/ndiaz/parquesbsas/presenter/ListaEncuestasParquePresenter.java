package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaEncuestasParqueContract;
import com.example.ndiaz.parquesbsas.model.Calificacion;
import com.example.ndiaz.parquesbsas.model.CalificacionEncuesta;
import com.example.ndiaz.parquesbsas.model.Encuesta;

import java.lang.ref.WeakReference;
import java.util.List;

public class ListaEncuestasParquePresenter extends BasePresenterImp implements
        ListaEncuestasParqueContract.Presenter {

    private WeakReference<ListaEncuestasParqueContract.View> view;
    private ListaEncuestasParqueContract.Interactor interactor;

    public ListaEncuestasParquePresenter(ListaEncuestasParqueContract.View view,
                                         ListaEncuestasParqueContract.Interactor interactor) {
        this.view = new WeakReference<>(view);
        this.interactor = interactor;
    }

    @Override
    public void doGetEncuestasParaCalificar(int idParque, int idUsuario) {
        interactor.getEncuestasParaCalificar(idParque, idUsuario, new BaseCallback<List<Encuesta>>() {
            @Override
            public void onSuccess(List<Encuesta> encuestasParaCalificar) {
                view.get().setEncuestasParaCalificar(encuestasParaCalificar);
                view.get().updateButtonCalificarEncuestaVisibility();
            }

            @Override
            public void onError(String message) {
                view.get().clearEncuestasParaCalificarList();
                view.get().updateButtonCalificarEncuestaVisibility();
                // TODO: 16/06/2018 Retry
            }
        });
    }

    @Override
    public void doGetCalificaciones() {
        interactor.getCalificaciones(new BaseCallback<List<Calificacion>>() {
            @Override
            public void onSuccess(List<Calificacion> calificaciones) {
                view.get().setCalificaciones(calificaciones);
                view.get().updateButtonCalificarEncuestaVisibility();
            }

            @Override
            public void onError(String message) {
                // TODO: 16/06/2018 Retry
            }
        });
    }

    @Override
    public void doGetEncuestasByParque(int idParque, final boolean refreshData) {
        interactor.getEncuestasByParque(idParque, new BaseCallback<List<Encuesta>>() {
            @Override
            public void onSuccess(List<Encuesta> encuestas) {
                if (!refreshData) {
                    view.get().showEncuestas(encuestas);
                } else {
                    view.get().refreshEncuestas(encuestas);
                }
            }

            @Override
            public void onError(String message) {
                view.get().showEmptyContainer();
            }
        });
    }

    @Override
    public void doGetEstadisticasEncuesta(int idParque, Encuesta encuesta) {
        interactor.getEstadisticasEncuesta(idParque, encuesta.getIdEncuesta(), new BaseCallback<Calificacion>() {
            @Override
            public void onSuccess(Calificacion calificacion) {
                view.get().showEstadisticasEncuesta(encuesta, calificacion);
            }

            @Override
            public void onError(String message) {
                view.get().showMessage(message);
            }
        });
    }

    @Override
    public void doCreateCalificacionEncuesta(CalificacionEncuesta calificacionEncuesta) {
        interactor.createCalificacionEncuesta(calificacionEncuesta, new BaseCallback<String>() {
            @Override
            public void onSuccess(String msg) {
                view.get().getEncuestasByParque(true);
                view.get().getEncuestasParaCalificar();
                view.get().closeCalificarEncuestaDialog();
                view.get().showSuccessInsertCalificacionEncuesta(msg);
            }

            @Override
            public void onError(String message) {
                view.get().getEncuestasParaCalificar();
                view.get().showFailInsertCalificacionEncuesta(message);
            }
        });
    }
}

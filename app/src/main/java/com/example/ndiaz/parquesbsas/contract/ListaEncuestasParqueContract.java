package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Calificacion;
import com.example.ndiaz.parquesbsas.model.CalificacionEncuesta;
import com.example.ndiaz.parquesbsas.model.Encuesta;

import java.util.List;

public interface ListaEncuestasParqueContract {

    interface View extends BaseView {
        void showEncuestas(List<Encuesta> encuestas);

        void showMessage(String message);

        void showEmptyContainer();

        void refreshEncuestas(List<Encuesta> encuestas);

        void showEstadisticasEncuesta(Encuesta encuesta, Calificacion calificacion);

        void setEncuestasParaCalificar(List<Encuesta> encuestasParaCalificar);

        void setCalificaciones(List<Calificacion> calificaciones);

        void updateButtonCalificarEncuestaVisibility();

        void closeCalificarEncuestaDialog();

        void showSuccessInsertCalificacionEncuesta(String msg);

        void getEncuestasByParque(boolean b);

        void showFailInsertCalificacionEncuesta(String message);

        void getEncuestasParaCalificar();

        void clearEncuestasParaCalificarList();
    }

    interface Presenter extends BasePresenter {
        void doGetEncuestasParaCalificar(int idParque, int idUsuario);

        void doGetCalificaciones();

        void doGetEncuestasByParque(int idParque, boolean refreshData);

        void doGetEstadisticasEncuesta(int idParque, Encuesta idEncuesta);

        void doCreateCalificacionEncuesta(CalificacionEncuesta calificacionEncuesta);
    }

    interface Interactor extends BaseInteractor {
        void getEncuestasParaCalificar(int idParque, int idUsuario, BaseCallback<List<Encuesta>> callback);

        void getCalificaciones(BaseCallback<List<Calificacion>> callback);

        void getEncuestasByParque(int idParque, BaseCallback<List<Encuesta>> callback);

        void getEstadisticasEncuesta(int idParque, int idEncuesta, BaseCallback<Calificacion> callback);

        void createCalificacionEncuesta(CalificacionEncuesta calificacionEncuesta, BaseCallback<String> callback);
    }

}

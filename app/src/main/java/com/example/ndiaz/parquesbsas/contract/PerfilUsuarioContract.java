package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.EmptyCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Documento;
import com.example.ndiaz.parquesbsas.model.Usuario;

import java.util.List;

public interface PerfilUsuarioContract {

    interface View extends BaseView {
        void showDocTypesError(String message);

        void setDocTypesList(List<Documento> documentos);

        void fillSpinner(String[] docTypes);

        void updateName(Usuario usuario);

        void updateDoc(Documento documento);

        void showSuccessPasswordUpdated();

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void doUpdateName(Usuario usuario);

        void doUpdateDoc(Documento documento);

        void doUpdatePassword(Usuario usuario);

        void doDeleteCuenta(int idUsuario);

        void doGetDocTypes();
    }

    interface Interactor extends BaseInteractor {
        void updateName(Usuario usuario, EmptyCallback callback);

        void updateDoc(Documento documento, EmptyCallback callback);

        void updatePassword(Usuario usuario, EmptyCallback callback);

        void deleteCuenta(int idUsuario, BaseCallback<String> callback);

        void getDocTypes(BaseCallback<List<Documento>> callback);
    }

}

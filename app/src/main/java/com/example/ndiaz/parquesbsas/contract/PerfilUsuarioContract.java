package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.EmptyCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Documento;
import com.example.ndiaz.parquesbsas.model.UsuarioPassword;

import java.util.List;

public interface PerfilUsuarioContract {

    interface View extends BaseView {
        void showDocTypesError(String message);

        void setDocTypesList(List<Documento> documentos);

        void fillSpinner(String[] docTypes);

        void updateName(String nombre, String apellido);

        void updateDoc(Documento documento);

        void showSuccessPasswordUpdated();

        void showMessage(String message);

        void showToastMessage(String message);

        void navigateToLogin();
    }

    interface Presenter extends BasePresenter {
        void doUpdateName(int idUsuario, String nombre, String apellido);

        void doUpdateDoc(int idUsuario, Documento documento);

        void doUpdatePassword(UsuarioPassword usuario);

        void doDeleteCuenta(int idUsuario);

        void doGetDocTypes();

        void doCleanAutoLoginUserData();

        void doLogout(ParquesApplication parquesApplication);
    }

    interface Interactor extends BaseInteractor {
        void updateName(int idUsuario, String nombre, String apellido, EmptyCallback callback);

        void updateDoc(int idUsuario, Documento documento, EmptyCallback callback);

        void updatePassword(UsuarioPassword usuario, EmptyCallback callback);

        void deleteCuenta(int idUsuario, BaseCallback<String> callback);

        void getDocTypes(BaseCallback<List<Documento>> callback);

        void cleanAutoLoginUserData();
    }

}

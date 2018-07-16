package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.Documento;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Usuario;

import java.util.List;

public interface CreateUserContract {

    interface View extends BaseView {
        void navigateToHome();

        void showCreateUserError(String message);

        void fillSpinner(String[] docTypes);

        void setDocTypes(List<Documento> documentos);
    }

    interface Presenter extends BasePresenter {
        void doCreateUser(Usuario usuario);

        void doGetDocTypes();

        void doSaveUserData(Usuario usuario);
    }

    interface Interactor extends BaseInteractor {
        void createUser(Usuario usuario, BaseCallback<NetworkResponse> callback);

        void getDocTypes(BaseCallback<NetworkResponse<List<Documento>>> callback);

        void saveUserData(Usuario usuario);
    }

}

package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.CreateUserContract;
import com.example.ndiaz.parquesbsas.model.Documento;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Usuario;

import java.util.List;

import static com.example.ndiaz.parquesbsas.constants.HTTPConstants.STATUS_OK;

public class CreateUserPresenter extends BasePresenterImp
        implements CreateUserContract.Presenter {

    private CreateUserContract.View createUserView;
    private CreateUserContract.Interactor createUserInteractor;

    public CreateUserPresenter(CreateUserContract.View createUserView, CreateUserContract.Interactor createUserInteractor) {
        this.createUserView = createUserView;
        this.createUserInteractor = createUserInteractor;
    }

    @Override
    public void doCreateUser(final Usuario usuario) {
        createUserInteractor.createUser(usuario, new BaseCallback<NetworkResponse>() {
            @Override
            public void onSuccess(NetworkResponse networkResponse) {
                if (networkResponse.getStatus() == STATUS_OK) {
                    ParquesApplication.getInstance().setUser(usuario);
                    createUserView.navigateToHome();
                } else {
                    createUserView.showCreateUserError(networkResponse.getMessage());
                }
            }

            @Override
            public void onError(String message) {
                createUserView.showCreateUserError(message);
            }
        });
    }

    @Override
    public void doGetDocTypes() {
        createUserInteractor.getDocTypes(new BaseCallback<NetworkResponse<List<Documento>>>() {
            @Override
            public void onSuccess(NetworkResponse<List<Documento>> tiposDocumentosNetworkResponse) {
                if(tiposDocumentosNetworkResponse.getStatus() == STATUS_OK){
                    List<Documento> documentos = tiposDocumentosNetworkResponse.getResponse();
                    String[] docTypes = new String[documentos.size()];
                    for(int i = 0; i < documentos.size(); i++){
                        docTypes[i] = documentos.get(i).getTipoDocumento();
                    }
                    createUserView.fillSpinner(docTypes);
                    createUserView.setDocTypes(documentos);
                }else{
                    createUserView.showCreateUserError(tiposDocumentosNetworkResponse.getMessage());
                }
            }

            @Override
            public void onError(String message) {
                createUserView.showCreateUserError(message);
            }
        });
    }
}

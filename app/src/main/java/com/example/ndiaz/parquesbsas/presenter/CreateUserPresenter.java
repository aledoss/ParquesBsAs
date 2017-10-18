package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.CreateUserContract;
import com.example.ndiaz.parquesbsas.model.Usuario;

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
        createUserInteractor.createUser(usuario, new BaseCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean value) {
                ParquesApplication.getInstance().setUser(usuario);
                createUserView.navigateToHome();
            }

            @Override
            public void onError(String message) {
                createUserView.showCreateUserError(message);
            }
        });
    }

    @Override
    public void doGetDocTypes() {
        createUserInteractor.getDocTypes(new BaseCallback<String[]>() {
            @Override
            public void onSuccess(String[] docTypes) {
                createUserView.fillSpinner(docTypes);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}

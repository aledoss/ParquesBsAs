package com.example.ndiaz.parquesbsas.presenter;

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
    public void doCreateUser(Usuario usuario) {

    }
}

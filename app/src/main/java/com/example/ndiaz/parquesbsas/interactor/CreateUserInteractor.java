package com.example.ndiaz.parquesbsas.interactor;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.CreateUserContract;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;
import com.example.ndiaz.parquesbsas.preferences.PreferencesRepository;

public class CreateUserInteractor extends BaseInteractorImp
        implements CreateUserContract.Interactor {

    private PreferencesRepository preferencesRepository;
    private NetworkServiceImp networkServiceImp;

    public CreateUserInteractor(PreferencesRepository preferencesRepository, NetworkServiceImp networkServiceImp) {
        super(preferencesRepository, networkServiceImp);
        this.preferencesRepository = preferencesRepository;
        this.networkServiceImp = networkServiceImp;
    }


    @Override
    public void createUser(Usuario usuario, BaseCallback<Boolean> callback) {

    }
}

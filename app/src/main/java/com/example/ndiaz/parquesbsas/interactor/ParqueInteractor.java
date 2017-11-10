package com.example.ndiaz.parquesbsas.interactor;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ParqueContract;
import com.example.ndiaz.parquesbsas.model.ParqueComponente;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import java.util.List;

public class ParqueInteractor extends BaseInteractorImp implements ParqueContract.Interactor {

    private NetworkServiceImp networkServiceImp;

    public ParqueInteractor(NetworkServiceImp networkServiceImp) {
        this.networkServiceImp = networkServiceImp;
    }

    @Override
    public void getParqueComponents(BaseCallback<List<ParqueComponente>> callback) {
        // TODO: 09/11/2017 Obtener los componentes del parque desde el servidor
    }
}

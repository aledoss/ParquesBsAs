package com.example.ndiaz.parquesbsas.interactor;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.EmptyCallback;
import com.example.ndiaz.parquesbsas.contract.FiltroParqueContract;
import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.model.Feria;
import com.example.ndiaz.parquesbsas.model.ParqueFilter;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;

import java.util.List;

public class FiltroParqueInteractor extends BaseInteractorImp
        implements FiltroParqueContract.Interactor {

    private static final String TAG = FiltroParqueInteractor.class.getSimpleName();
    private NetworkServiceImp networkServiceImp;

    public FiltroParqueInteractor(NetworkServiceImp networkServiceImp) {
        this.networkServiceImp = networkServiceImp;
    }


    @Override
    public void getActividades(BaseCallback<List<Actividad>> callback) {

    }

    @Override
    public void getFerias(BaseCallback<List<Feria>> callback) {

    }

    @Override
    public void filter(ParqueFilter filter, EmptyCallback callback) {

    }
}

package com.example.ndiaz.parquesbsas.interactor;

import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.database.DBHelper;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;
import com.example.ndiaz.parquesbsas.preferences.PreferencesRepository;

import io.reactivex.disposables.CompositeDisposable;

public class BaseInteractorImp implements BaseInteractor {

    private CompositeDisposable compositeDisposable;
    private DBHelper dbHelper;
    private PreferencesRepository preferencesRepository;
    private NetworkServiceImp networkServiceImp;

    public BaseInteractorImp() {
        this.compositeDisposable = new CompositeDisposable();
    }

    public BaseInteractorImp(CompositeDisposable compositeDisposable, DBHelper dbHelper, NetworkServiceImp networkServiceImp) {
        this.compositeDisposable = compositeDisposable;
        this.dbHelper = dbHelper;
        this.networkServiceImp = networkServiceImp;
    }

    public BaseInteractorImp(CompositeDisposable compositeDisposable, DBHelper dbHelper,
                             PreferencesRepository preferencesRepository, NetworkServiceImp networkServiceImp) {
        this.compositeDisposable = compositeDisposable;
        this.dbHelper = dbHelper;
        this.preferencesRepository = preferencesRepository;
        this.networkServiceImp = networkServiceImp;
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public PreferencesRepository getPreferencesRepository() {
        return preferencesRepository;
    }

    public void setPreferencesRepository(PreferencesRepository preferencesRepository) {
        this.preferencesRepository = preferencesRepository;
    }

    public NetworkServiceImp getNetworkServiceImp() {
        return networkServiceImp;
    }

    public void setNetworkServiceImp(NetworkServiceImp networkServiceImp) {
        this.networkServiceImp = networkServiceImp;
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

}

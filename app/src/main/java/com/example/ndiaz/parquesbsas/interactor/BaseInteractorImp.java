package com.example.ndiaz.parquesbsas.interactor;

import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.database.DBHelper;
import com.example.ndiaz.parquesbsas.network.NetworkServiceImp;
import com.example.ndiaz.parquesbsas.preferences.PreferencesRepository;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseInteractorImp implements BaseInteractor {

    private CompositeDisposable compositeDisposable;
    private DBHelper dbHelper;
    private PreferencesRepository preferencesRepository;
    private NetworkServiceImp networkServiceImp;

    public BaseInteractorImp() {
        initializeCompositeDisposable();
    }

    public BaseInteractorImp(DBHelper dbHelper, NetworkServiceImp networkServiceImp) {
        initializeCompositeDisposable();
        this.dbHelper = dbHelper;
        this.networkServiceImp = networkServiceImp;
    }

    public BaseInteractorImp(PreferencesRepository preferencesRepository,
                             NetworkServiceImp networkServiceImp) {
        initializeCompositeDisposable();
        this.preferencesRepository = preferencesRepository;
        this.networkServiceImp = networkServiceImp;
    }

    public BaseInteractorImp(DBHelper dbHelper, PreferencesRepository preferencesRepository,
                             NetworkServiceImp networkServiceImp) {
        initializeCompositeDisposable();
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

    public void initializeCompositeDisposable() {
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    public void addDisposable(Disposable disposable){
        this.compositeDisposable.add(disposable);
    }

}

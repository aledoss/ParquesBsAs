package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.HomeContract;
import com.example.ndiaz.parquesbsas.model.Parque;

import java.util.List;

/**
 * Created by nicolasd on 30/10/2017.
 */

public class HomePresenter extends BasePresenterImp implements HomeContract.Presenter {

    private HomeContract.View homeView;
    private HomeContract.Interactor homeInteractor;

    public HomePresenter(HomeContract.View homeView, HomeContract.Interactor homeInteractor) {
        this.homeView = homeView;
        this.homeInteractor = homeInteractor;
    }

    @Override
    public void doGetParques() {
        homeView.showProgressDialog();
        homeInteractor.getParques(new BaseCallback<List<Parque>>() {
            @Override
            public void onSuccess(List<Parque> parques) {
                homeView.loadParques(parques);
            }

            @Override
            public void onError(String message) {
                homeView.showMessage(message);
                homeView.hideProgressDialog();
            }
        });
    }

    @Override
    public void doGetParque(int parqueId) {
        homeInteractor.getParque(parqueId, new BaseCallback<Parque>() {
            @Override
            public void onSuccess(Parque parque) {
                homeView.showParquesDialog(parque);
            }

            @Override
            public void onError(String message) {
                homeView.showMessage(message);
            }
        });
    }
}

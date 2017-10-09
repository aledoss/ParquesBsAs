package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.contract.LoginContract;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View loginView;
    private LoginContract.Interactor loginInteractor;

    public LoginPresenter(LoginContract.View loginView, LoginContract.Interactor loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }
}

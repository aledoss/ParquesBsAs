package com.example.ndiaz.parquesbsas.presenter;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.EmptyCallback;
import com.example.ndiaz.parquesbsas.contract.PerfilUsuarioContract;
import com.example.ndiaz.parquesbsas.model.Documento;
import com.example.ndiaz.parquesbsas.model.UsuarioPassword;

import java.util.List;

public class PerfilUsuarioPresenter extends BasePresenterImp
        implements PerfilUsuarioContract.Presenter {

    private PerfilUsuarioContract.View view;
    private PerfilUsuarioContract.Interactor interactor;

    public PerfilUsuarioPresenter(PerfilUsuarioContract.View view, PerfilUsuarioContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void doUpdateName(int idUsuario, String nombre, String apellido) {
        view.showProgressDialog();
        interactor.updateName(idUsuario, nombre, apellido, new EmptyCallback() {
            @Override
            public void onSuccess() {
                view.updateName(nombre, apellido);
                view.hideProgressDialog();
            }

            @Override
            public void onError(String message) {
                view.hideProgressDialog();
                view.showMessage(message);
            }
        });
    }

    @Override
    public void doUpdateDoc(int idUsuario, Documento documento) {
        view.showProgressDialog();
        interactor.updateDoc(idUsuario, documento, new EmptyCallback() {
            @Override
            public void onSuccess() {
                view.updateDoc(documento);
                view.hideProgressDialog();
            }

            @Override
            public void onError(String message) {
                view.hideProgressDialog();
                view.showMessage(message);
            }
        });
    }

    @Override
    public void doUpdatePassword(UsuarioPassword usuario) {
        view.showProgressDialog();
        interactor.updatePassword(usuario, new EmptyCallback() {
            @Override
            public void onSuccess() {
                view.hideProgressDialog();
                view.showSuccessPasswordUpdated();
            }

            @Override
            public void onError(String message) {
                view.hideProgressDialog();
                view.showMessage(message);
            }
        });
    }

    @Override
    public void doDeleteCuenta(int idUsuario) {
        view.showProgressDialog();
        interactor.deleteCuenta(idUsuario, new BaseCallback<String>() {
            @Override
            public void onSuccess(String message) {
                view.hideProgressDialog();
                doCleanAutoLoginUserData();
                view.showToastMessage(message);
                view.navigateToLogin();
            }

            @Override
            public void onError(String message) {
                view.hideProgressDialog();
                view.showMessage(message);
            }
        });
    }

    @Override
    public void doGetDocTypes() {
        interactor.getDocTypes(new BaseCallback<List<Documento>>() {
            @Override
            public void onSuccess(List<Documento> documentos) {
                view.setDocTypesList(documentos);
            }

            @Override
            public void onError(String message) {
                view.showDocTypesError(message);
            }
        });
    }

    @Override
    public void doLogout(ParquesApplication parquesApplication) {
        if (parquesApplication.isLoggedWithGoogle()) {
            parquesApplication
                    .getGoogleSignInClient()
                    .signOut()
                    .addOnSuccessListener(aVoid -> ParquesApplication.getInstance().setLoggedWithGoogle(false));
        }
        doCleanAutoLoginUserData();
        view.navigateToLogin();
    }

    @Override
    public void doCleanAutoLoginUserData() {
        interactor.cleanAutoLoginUserData();
    }
}

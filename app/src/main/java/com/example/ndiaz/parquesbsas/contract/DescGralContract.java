package com.example.ndiaz.parquesbsas.contract;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.callbacks.SingleCallback;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseInteractor;
import com.example.ndiaz.parquesbsas.contract.basecontract.BasePresenter;
import com.example.ndiaz.parquesbsas.contract.basecontract.BaseView;
import com.example.ndiaz.parquesbsas.model.parquelike.ParqueLike;

public interface DescGralContract {

    interface View extends BaseView {
        void showMessage(String message);

        void refreshLikeViews();

        void createDefaultParqueLike();

        void createIncreaseParqueLike();

        void createDecreaseParqueLike();

        void enableLikeButtons();
    }

    interface Presenter extends BasePresenter {
        void doUpdateParqueLike(int idParque, int idUsuario, boolean increase, ParqueLike parqueLike);

        void doGetParqueLikeStatus(int idParque, int idUsuario);
    }

    interface Interactor extends BaseInteractor {
        void updateParqueLike(int idParque, int idUsuario, boolean increase, ParqueLike parqueLike, BaseCallback callback);

        void updateSharedPrefParqueLike(int idParque, int idUsuario, boolean increase, boolean finalDefaultParque);

        void getParqueLikeStatus(int idParque, int idUsuario, SingleCallback<String> callback);
    }

}

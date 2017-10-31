package com.example.ndiaz.parquesbsas.interactor;

import android.content.Context;

import com.example.ndiaz.parquesbsas.database.DBHelper;
import com.example.ndiaz.parquesbsas.model.Parque;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.internal.operators.observable.ObservableJust;


/**
 * Created by nicolasd on 30/10/2017.
 */

public class RXDBInteractor {

    private DBHelper dbHelper;

    public RXDBInteractor(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public Single<Parque> getParque(int id) {
        return Single.defer(new ObservableJust<SingleSource<Parque>>(
                Single.just(dbHelper.getParque(id))));
    }

    public Single<List<Parque>> getParques() {
        return Single.defer(new ObservableJust<SingleSource<List<Parque>>>(
                Single.<List<Parque>>just(dbHelper.getAllParques())));
    }


    public Single<Boolean> saveParques(List<Parque> parques) {
        return Single.defer(new ObservableJust<SingleSource<Boolean>>(
                Single.just(dbHelper.insertarParques(parques))
        ));
    }
}

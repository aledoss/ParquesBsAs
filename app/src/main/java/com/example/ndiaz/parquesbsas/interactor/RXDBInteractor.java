package com.example.ndiaz.parquesbsas.interactor;

import android.content.Context;

import com.example.ndiaz.parquesbsas.database.DBHelper;
import com.example.ndiaz.parquesbsas.model.Parque;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.internal.operators.observable.ObservableJust;

public class RXDBInteractor {

    private DBHelper dbHelper;

    public RXDBInteractor(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public Single<List<Parque>> getParques() {
        return Single.fromCallable(() -> {
            List<Parque> parques = dbHelper.getAllParques();

            return parques == null ? new ArrayList<>() : parques;
        });
    }


    public Single<Boolean> saveParques(List<Parque> parques) {
        return Single.defer(new ObservableJust<SingleSource<Boolean>>(
                Single.just(dbHelper.insertarParques(parques))
        ));
    }
}

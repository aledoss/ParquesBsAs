package com.example.ndiaz.parquesbsas.interactor;

import android.util.Log;

import com.example.ndiaz.parquesbsas.callbacks.BaseCallback;
import com.example.ndiaz.parquesbsas.contract.ListaParquesContract;
import com.example.ndiaz.parquesbsas.model.Parque;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class ListaParquesInteractor extends BaseInteractorImp implements ListaParquesContract.Interactor {

    private static final String TAG = ListaParquesInteractor.class.getSimpleName();
    private RXDBInteractor rxdbInteractor;

    public ListaParquesInteractor(RXDBInteractor rxdbInteractor) {
        this.rxdbInteractor = rxdbInteractor;
    }

    @Override
    public void getParques(final BaseCallback<List<Parque>> callback) {
        rxdbInteractor
                .getParques()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Parque>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Parque> parques) {
                        callback.onSuccess(parques);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                        Log.e(TAG, "getParques onError:", e);
                    }
                });
    }

    @Override
    public void getParquesFiltered(final List<Parque> itemList, final String newText, final BaseCallback<List<Parque>> callback) {
        Observable.fromCallable(new Callable<List<Parque>>() {
            @Override
            public List<Parque> call() throws Exception {
                return itemList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(new Function<List<Parque>, List<Parque>>() {
                    @Override
                    public List<Parque> apply(List<Parque> parques) throws Exception {
                        return parques;
                    }
                })
                .filter(new Predicate<Parque>() {
                    @Override
                    public boolean test(Parque parque) throws Exception {
                        return Pattern.compile(Pattern.quote(newText), Pattern.CASE_INSENSITIVE).matcher(parque.getNombre()).find();
                    }
                })
                .toList()
                .subscribe(new SingleObserver<List<Parque>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Parque> parques) {
                        callback.onSuccess(parques);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                        Log.e(TAG, "getParquesFiltered onError: ", e);
                    }
                });
    }
}

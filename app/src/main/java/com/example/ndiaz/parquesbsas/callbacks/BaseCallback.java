package com.example.ndiaz.parquesbsas.callbacks;

public interface BaseCallback<T> {

    void onSuccess(T value);

    void onError(String message);

}

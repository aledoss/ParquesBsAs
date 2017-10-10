package com.example.ndiaz.parquesbsas.network;

import com.example.ndiaz.parquesbsas.model.Parque;

import java.util.List;

import io.reactivex.Single;

public class NetworkServiceImp {

    private RetrofitService retrofitService;

    public NetworkServiceImp(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    public Single<List<Parque>> getParque() {
        return retrofitService
                .getJson()
                .getParques();
    }
}

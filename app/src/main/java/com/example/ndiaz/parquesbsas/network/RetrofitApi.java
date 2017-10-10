package com.example.ndiaz.parquesbsas.network;

import com.example.ndiaz.parquesbsas.model.Parque;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RetrofitApi {

    @GET("/ws_parques/todos")
    Single<List<Parque>> getParques();

}

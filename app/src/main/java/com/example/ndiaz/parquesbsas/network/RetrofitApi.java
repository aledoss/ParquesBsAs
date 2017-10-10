package com.example.ndiaz.parquesbsas.network;

import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.Usuario;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitApi {

    @GET("/ws_parques/todos")
    Single<List<Parque>> getParques();

    @POST("/ws_parques/login")
    Single<Usuario> loginUser(@Body Usuario usuario);
}

package com.example.ndiaz.parquesbsas.network;

import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.TiposDocumento;
import com.example.ndiaz.parquesbsas.model.Usuario;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitApi {

    @GET("parquesapi/getParques")
    Single<NetworkResponse<List<Parque>>> getParques();

    @GET("parquesapi/getDocTypes")
    Single<NetworkResponse<List<TiposDocumento>>> getDocTypes();

    @POST("parquesapi/login")
    Single<NetworkResponse<Usuario>> loginUser(@Body Usuario usuario);

    @POST("parquesapi/createUser")
    Single<NetworkResponse> createUser(@Body Usuario usuario);
}

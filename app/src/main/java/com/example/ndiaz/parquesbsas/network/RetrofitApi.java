package com.example.ndiaz.parquesbsas.network;

import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.ParqueComponente;
import com.example.ndiaz.parquesbsas.model.Reclamo;
import com.example.ndiaz.parquesbsas.model.TiposDocumento;
import com.example.ndiaz.parquesbsas.model.Usuario;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface RetrofitApi {

    @GET("parquesapi/getDocTypes")
    Single<NetworkResponse<List<TiposDocumento>>> getDocTypes();

    @POST("parquesapi/login")
    Single<NetworkResponse<Usuario>> loginUser(@Body Usuario usuario);

    @POST("parquesapi/createUser")
    Single<NetworkResponse> createUser(@Body Usuario usuario);

    @GET("parquesapi/getParques")
    Single<NetworkResponse<List<Parque>>> getParques();

    @GET("parquesapi/getParque/{id}")
    Single<NetworkResponse<Parque>> getParque(@Path("id") int idParque);

    @GET("parquesapi/getParqueComponents/{id}")
    Single<NetworkResponse<List<ParqueComponente>>> getParqueComponentes(@Path("id") int idParque);

    @GET("parquesapi/getReclamosByParque/{id}")
    Single<NetworkResponse<List<Reclamo>>> getReclamosByParque(@Path("id") int idParque);
}

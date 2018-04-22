package com.example.ndiaz.parquesbsas.network;

import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.model.EstacionSaludable;
import com.example.ndiaz.parquesbsas.model.Feria;
import com.example.ndiaz.parquesbsas.model.FeriaItinerante;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.ParqueComponente;
import com.example.ndiaz.parquesbsas.model.ParqueLikeBody;
import com.example.ndiaz.parquesbsas.model.PuntoVerde;
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

    @GET("parquesapi/getReclamosDesc")
    Single<NetworkResponse<List<Reclamo>>> getReclamos();

    @POST("parquesapi/createReclamo")
    Single<NetworkResponse> insertReclamo(@Body Reclamo reclamo);

    @GET("parquesapi/getActividadesByParque/{id}")
    Single<NetworkResponse<List<Actividad>>> getActividades(@Path("id") int idParque);

    @GET("parquesapi/getHorariosByParqueActividad/{idParque}/{idActividad}")
    Single<NetworkResponse<List<Actividad>>> getHorarios(@Path("idParque") int idParque,
                                                         @Path("idActividad") int idActividad);

    @GET("parquesapi/getFeriasByParque/{id}")
    Single<NetworkResponse<List<Feria>>> getFerias(@Path("id") int idParque);

    @GET("parquesapi/getFeriasItinerantesByParque/{id}")
    Single<NetworkResponse<List<FeriaItinerante>>> getFeriasItinerantes(@Path("id") int idParque);

    @GET("parquesapi/getEstSaludablesByParque/{id}")
    Single<NetworkResponse<List<EstacionSaludable>>> getEstSaludables(@Path("id") int idParque);

    @GET("parquesapi/getReclamosByUsuario/{id}")
    Single<NetworkResponse<List<Reclamo>>> getReclamosByUsuario(@Path("id") int idUsuario);

    @GET("parquesapi/getPuntosVerdesByParque/{id}")
    Single<NetworkResponse<List<PuntoVerde>>> getPuntosVerdes(@Path("id") int idParque);

    @POST("parquesapi/updateParqueLike/")
    Single<NetworkResponse> updateParqueLike(@Body ParqueLikeBody parqueLikeBody);
}

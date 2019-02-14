package com.example.ndiaz.parquesbsas.network;

import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.model.Calificacion;
import com.example.ndiaz.parquesbsas.model.CalificacionEncuesta;
import com.example.ndiaz.parquesbsas.model.Documento;
import com.example.ndiaz.parquesbsas.model.Encuesta;
import com.example.ndiaz.parquesbsas.model.EstacionSaludable;
import com.example.ndiaz.parquesbsas.model.Feria;
import com.example.ndiaz.parquesbsas.model.FeriaItinerante;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.ParqueComponente;
import com.example.ndiaz.parquesbsas.model.ParqueFilter;
import com.example.ndiaz.parquesbsas.model.ParqueLikeBody;
import com.example.ndiaz.parquesbsas.model.PuntoVerde;
import com.example.ndiaz.parquesbsas.model.Reclamo;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.example.ndiaz.parquesbsas.model.UsuarioPassword;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

interface RetrofitApi {

    @GET("ParquesApi/getDocTypes")
    Single<NetworkResponse<List<Documento>>> getDocTypes();

    @POST("ParquesApi/login")
    Single<NetworkResponse<Usuario>> loginUser(@Body Usuario usuario);

    @POST("ParquesApi/createUser")
    Single<NetworkResponse> createUser(@Body Usuario usuario);

    @POST("ParquesApi/updateUserName")
    Single<NetworkResponse> updateUserName(@Body Usuario usuario);

    @POST("ParquesApi/updateDocument/{id}")
    Single<NetworkResponse> updateUserDocument(@Path("id") int id, @Body Documento documento);

    @POST("ParquesApi/updatePassword")
    Single<NetworkResponse> updateUserPassword(@Body UsuarioPassword usuario);

    @POST("ParquesApi/deleteCuenta")
    Single<NetworkResponse> deleteUser(@Body Usuario usuario);

    @POST("ParquesApi/recoverPassword")
    Single<NetworkResponse<String>> recoverPassword(@Body Usuario usuario);

    @GET("ParquesApi/getParques")
    Single<NetworkResponse<List<Parque>>> getParques();

    @GET("ParquesApi/getParque/{id}")
    Single<NetworkResponse<Parque>> getParque(@Path("id") int idParque);

    @GET("ParquesApi/getParqueComponents/{id}")
    Single<NetworkResponse<List<ParqueComponente>>> getParqueComponentes(@Path("id") int idParque);

    @GET("ParquesApi/getReclamosByParque/{id}")
    Single<NetworkResponse<List<Reclamo>>> getReclamosByParque(@Path("id") int idParque);

    @GET("ParquesApi/getReclamosDesc")
    Single<NetworkResponse<List<Reclamo>>> getReclamos();

    @POST("ParquesApi/createReclamo")
    Single<NetworkResponse> insertReclamo(@Body Reclamo reclamo);

    @GET("ParquesApi/getActividadesByParque/{id}")
    Single<NetworkResponse<List<Actividad>>> getActividades(@Path("id") int idParque);

    @GET("ParquesApi/getHorariosByParqueActividad/{idParque}/{idActividad}")
    Single<NetworkResponse<List<Actividad>>> getHorarios(@Path("idParque") int idParque,
                                                         @Path("idActividad") int idActividad);

    @GET("ParquesApi/getFeriasByParque/{id}")
    Single<NetworkResponse<List<Feria>>> getFerias(@Path("id") int idParque);

    @GET("ParquesApi/getFeriasItinerantesByParque/{id}")
    Single<NetworkResponse<List<FeriaItinerante>>> getFeriasItinerantes(@Path("id") int idParque);

    @GET("ParquesApi/getEstSaludablesByParque/{id}")
    Single<NetworkResponse<List<EstacionSaludable>>> getEstSaludables(@Path("id") int idParque);

    @GET("ParquesApi/getReclamosByUsuario/{id}")
    Single<NetworkResponse<List<Reclamo>>> getReclamosByUsuario(@Path("id") int idUsuario);

    @GET("ParquesApi/getPuntosVerdesByParque/{id}")
    Single<NetworkResponse<List<PuntoVerde>>> getPuntosVerdes(@Path("id") int idParque);

    @POST("ParquesApi/updateParqueLike/")
    Single<NetworkResponse> updateParqueLike(@Body ParqueLikeBody parqueLikeBody);

    @GET("ParquesApi/getEncuestasParaCalificarByParqueAndUsuario/{idParque}/{idEncuesta}")
    Single<NetworkResponse<List<Encuesta>>> getEncuestasParaCalificar(@Path("idParque") int idParque,
                                                                      @Path("idEncuesta") int idUsuario);

    @GET("ParquesApi/getCalificaciones/")
    Single<NetworkResponse<List<Calificacion>>> getCalificaciones();

    @GET("ParquesApi/getEncuestasByParque/{id}")
    Single<NetworkResponse<List<Encuesta>>> getEncuestasByParque(@Path("id") int idParque);

    @GET("ParquesApi/getEstadisticasEncuestaByParque/{idParque}/{idEncuesta}")
    Single<NetworkResponse<Calificacion>> getEstadisticasEncuesta(@Path("idParque") int idParque,
                                                                  @Path("idEncuesta") int idEncuesta);

    @POST("ParquesApi/insertarCalificacionEncuesta")
    Single<NetworkResponse<Boolean>> createCalificacionEncuesta(@Body CalificacionEncuesta calificacionEncuesta);

    @GET("ParquesApi/getActividadesToFilter")
    Single<NetworkResponse<List<Actividad>>> getActividadesToFilter();

    @GET("ParquesApi/getFeriasToFilter")
    Single<NetworkResponse<List<Feria>>> getFeriasToFilter();

    @POST("ParquesApi/filterParques")
    Single<NetworkResponse<List<Parque>>> filterParques(@Body ParqueFilter parqueFilter);

    @POST("ParquesApi/loginWithGoogle")
    Single<NetworkResponse<Usuario>> loginWithGoogle(@Body Usuario usuario);

    @POST("ParquesApi/vinculateWithGoogle")
    Single<NetworkResponse<Usuario>> vinculateWithGoogle(@Body Usuario usuario);

    @POST("ParquesApi/deleteReclamo")
    Single<NetworkResponse<String>> deleteReclamo(@Body Reclamo reclamo);

    @Multipart
    @POST("ParquesApi/uploadFotoReclamo")
    Single<NetworkResponse<String>> uploadFotoReclamo(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file);
}

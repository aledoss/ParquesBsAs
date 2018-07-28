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

public class NetworkServiceImp {

    private RetrofitService retrofitService;

    public NetworkServiceImp(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    public Single<NetworkResponse<Usuario>> getUser(Usuario usuario) {
        return retrofitService
                .getClient()
                .loginUser(usuario);
    }

    public Single<NetworkResponse<List<Documento>>> getDocTypes() {
        return retrofitService
                .getClient()
                .getDocTypes();
    }

    public Single<NetworkResponse> createUser(Usuario usuario) {
        return retrofitService
                .getClient()
                .createUser(usuario);
    }

    public Single<NetworkResponse> updateUserName(Usuario usuario) {
        return retrofitService
                .getClient()
                .updateUserName(usuario);
    }

    public Single<NetworkResponse> updateUserDocument(int id, Documento documento) {
        return retrofitService
                .getClient()
                .updateUserDocument(id, documento);
    }

    public Single<NetworkResponse> updateUserPassword(UsuarioPassword usuario) {
        return retrofitService
                .getClient()
                .updateUserPassword(usuario);
    }

    public Single<NetworkResponse<String>> recoverPassword(Usuario usuario){
        return  retrofitService
                .getClient()
                .recoverPassword(usuario);
    }

    public Single<NetworkResponse> deleteUser(Usuario usuario) {
        return retrofitService
                .getClient()
                .deleteUser(usuario);
    }

    public Single<NetworkResponse<List<Parque>>> getParques() {
        return retrofitService
                .getClient()
                .getParques();
    }

    public Single<NetworkResponse<Parque>> getParque(int idParque) {
        return retrofitService
                .getClient()
                .getParque(idParque);
    }

    public Single<NetworkResponse<List<ParqueComponente>>> getParqueComponentes(int idParque) {
        return retrofitService
                .getClient()
                .getParqueComponentes(idParque);
    }

    public Single<NetworkResponse<List<Reclamo>>> getReclamosByParque(int idParque) {
        return retrofitService
                .getClient()
                .getReclamosByParque(idParque);
    }

    public Single<NetworkResponse<List<Reclamo>>> getReclamos() {
        return retrofitService
                .getClient()
                .getReclamos();
    }

    public Single<NetworkResponse> insertReclamo(Reclamo reclamo) {
        return retrofitService
                .getClient()
                .insertReclamo(reclamo);
    }

    public Single<NetworkResponse<List<Actividad>>> getActividades(int idParque) {
        return retrofitService
                .getClient()
                .getActividades(idParque);
    }

    public Single<NetworkResponse<List<Actividad>>> getHorarios(int idParque, int idActividad) {
        return retrofitService
                .getClient()
                .getHorarios(idParque, idActividad);
    }

    public Single<NetworkResponse<List<Feria>>> getferias(int idParque) {
        return retrofitService
                .getClient()
                .getFerias(idParque);
    }

    public Single<NetworkResponse<List<FeriaItinerante>>> getFeriasItinerantes(int idParque) {
        return retrofitService
                .getClient()
                .getFeriasItinerantes(idParque);
    }

    public Single<NetworkResponse<List<EstacionSaludable>>> getEstSaludables(int idParque) {
        return retrofitService
                .getClient()
                .getEstSaludables(idParque);
    }

    public Single<NetworkResponse<List<Reclamo>>> getReclamosByUsuario(int idUsuario) {
        return retrofitService
                .getClient()
                .getReclamosByUsuario(idUsuario);
    }

    public Single<NetworkResponse<List<PuntoVerde>>> getPuntosVerdes(int idParque) {
        return retrofitService
                .getClient()
                .getPuntosVerdes(idParque);
    }

    public Single<NetworkResponse> updateParqueLike(ParqueLikeBody parqueLikeBody) {
        return retrofitService
                .getClient()
                .updateParqueLike(parqueLikeBody);
    }

    public Single<NetworkResponse<List<Encuesta>>> getEncuestasParaCalificar(int idParque, int idUsuario) {
        return retrofitService
                .getClient()
                .getEncuestasParaCalificar(idParque, idUsuario);
    }

    public Single<NetworkResponse<List<Calificacion>>> getCalificaciones() {
        return retrofitService
                .getClient()
                .getCalificaciones();
    }

    public Single<NetworkResponse<List<Encuesta>>> getEncuestasByParque(int idParque) {
        return retrofitService
                .getClient()
                .getEncuestasByParque(idParque);
    }

    public Single<NetworkResponse<Calificacion>> getEstadisticasEncuesta(int idParque, int idEncuesta) {
        return retrofitService
                .getClient()
                .getEstadisticasEncuesta(idParque, idEncuesta);
    }

    public Single<NetworkResponse<Boolean>> createCalificacionEncuesta(CalificacionEncuesta calificacionEncuesta) {
        return retrofitService
                .getClient()
                .createCalificacionEncuesta(calificacionEncuesta);
    }

    public Single<NetworkResponse<List<Actividad>>> getActividadesToFilter() {
        return retrofitService
                .getClient()
                .getActividadesToFilter();
    }

    public Single<NetworkResponse<List<Feria>>> getFeriasToFilter() {
        return retrofitService
                .getClient()
                .getFeriasToFilter();
    }

    public Single<NetworkResponse<List<Parque>>> filter(ParqueFilter parqueFilter){
        return retrofitService
                .getClient()
                .filter(parqueFilter);
    }
}

package com.example.ndiaz.parquesbsas.network;

import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.model.Feria;
import com.example.ndiaz.parquesbsas.model.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.ParqueComponente;
import com.example.ndiaz.parquesbsas.model.Reclamo;
import com.example.ndiaz.parquesbsas.model.TiposDocumento;
import com.example.ndiaz.parquesbsas.model.Usuario;

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

    public Single<NetworkResponse<List<TiposDocumento>>> getDocTypes() {
        return retrofitService
                .getClient()
                .getDocTypes();
    }

    public Single<NetworkResponse> createUser(Usuario usuario) {
        return retrofitService
                .getClient()
                .createUser(usuario);
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
}

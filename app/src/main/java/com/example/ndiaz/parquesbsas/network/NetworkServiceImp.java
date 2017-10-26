package com.example.ndiaz.parquesbsas.network;

import com.example.ndiaz.parquesbsas.gsonresult.NetworkResponse;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.Usuario;

import java.util.List;

import io.reactivex.Single;

public class NetworkServiceImp {

    private RetrofitService retrofitService;

    public NetworkServiceImp(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    public Single<List<Parque>> getParque() {
        return retrofitService
                .getClient()
                .getParques();
    }

    public Single<NetworkResponse<Usuario>> getUser(Usuario usuario){
        return retrofitService
                .getClient()
                .loginUser(usuario);
    }
}

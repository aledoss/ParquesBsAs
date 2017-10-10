package com.example.ndiaz.parquesbsas.network;

import com.example.ndiaz.parquesbsas.model.Parque;

import java.util.List;

import io.reactivex.Observable;

public interface NetworkService {

    Observable<List<Parque>> getParques();

}

package com.example.ndiaz.parquesbsas.cluster;

import com.example.ndiaz.parquesbsas.model.Parque;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ParqueCluster implements ClusterItem {

    private Parque parque;

    public ParqueCluster(Parque parque) {
        this.parque = parque;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(Double.parseDouble(parque.getLatitud()), Double.parseDouble(parque.getLongitud()));
    }

    @Override
    public String getTitle() {
        return parque.getNombre();
    }

    @Override
    public String getSnippet() {
        return "";
    }

    public Parque getParque() {
        return parque;
    }
}

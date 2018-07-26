package com.example.ndiaz.parquesbsas.model.filter_checkbox;

import com.example.ndiaz.parquesbsas.model.Actividad;

import java.util.List;

public class ActividadesFilterCheckbox implements BaseFilterCheckbox {

    private List<Actividad> actividades;

    public ActividadesFilterCheckbox(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    @Override
    public String getTitle(int position) {
        return actividades.get(position).getNombre();
    }

    @Override
    public int getId(int position) {
        return actividades.get(position).getId();
    }

    @Override
    public int getCount() {
        return actividades.size();
    }
}

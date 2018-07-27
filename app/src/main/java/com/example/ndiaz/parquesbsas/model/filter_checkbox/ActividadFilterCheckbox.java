package com.example.ndiaz.parquesbsas.model.filter_checkbox;

import com.example.ndiaz.parquesbsas.model.Actividad;

public class ActividadFilterCheckbox implements BaseFilterCheckbox {

    private Actividad actividad;
    private boolean isChecked;

    public ActividadFilterCheckbox(Actividad actividad) {
        this.actividad = actividad;
    }

    @Override
    public String getTitle() {
        return actividad.getNombre();
    }

    @Override
    public int getId() {
        return actividad.getId();
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public Object getObject() {
        return actividad;
    }
}

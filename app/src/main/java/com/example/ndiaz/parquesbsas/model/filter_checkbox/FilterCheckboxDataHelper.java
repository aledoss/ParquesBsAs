package com.example.ndiaz.parquesbsas.model.filter_checkbox;

import com.example.ndiaz.parquesbsas.model.Actividad;
import com.example.ndiaz.parquesbsas.model.Feria;

import java.util.ArrayList;
import java.util.List;

public class FilterCheckboxDataHelper {

    public List<BaseFilterCheckbox> createActividadesFilterCheckBox(List<Actividad> actividades) {
        List<BaseFilterCheckbox> actividadesFilterCheckbox = new ArrayList<>();

        for (Actividad actividad : actividades) {
            actividadesFilterCheckbox.add(new ActividadFilterCheckbox(actividad));
        }

        return actividadesFilterCheckbox;
    }

    public List<BaseFilterCheckbox> createFeriasFilterCheckbox(List<Feria> ferias) {
        List<BaseFilterCheckbox> feriasFilterCheckbox = new ArrayList<>();

        for (Feria feria : ferias) {
            feriasFilterCheckbox.add(new FilterCheckboxFeria(feria));
        }

        return feriasFilterCheckbox;
    }

    public List<Actividad> getActividadesMarcadas(List<BaseFilterCheckbox> items) {
        List<Actividad> actividadesMarcadas = new ArrayList<>();

        for (BaseFilterCheckbox baseFilterCheckbox : items) {
            if (baseFilterCheckbox.isChecked()) {
                actividadesMarcadas.add((Actividad) baseFilterCheckbox.getObject());
            }
        }

        return actividadesMarcadas;
    }

    public List<Feria> getFeriasMarcadas(List<BaseFilterCheckbox> items) {
        List<Feria> feriasMarcadas = new ArrayList<>();

        for (BaseFilterCheckbox baseFilterCheckbox : items) {
            if (baseFilterCheckbox.isChecked()) {
                feriasMarcadas.add((Feria) baseFilterCheckbox.getObject());
            }
        }

        return feriasMarcadas;
    }
}

package com.example.ndiaz.parquesbsas.model.filter_checkbox;

import com.example.ndiaz.parquesbsas.model.Feria;

public class FilterCheckboxFeria implements BaseFilterCheckbox {

    private Feria feria;
    private boolean isChecked;

    public FilterCheckboxFeria(Feria feria) {
        this.feria = feria;
    }

    @Override
    public String getTitle() {
        return feria.getTipo();
    }

    @Override
    public int getId() {
        return feria.getIdFeria();
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
        return feria;
    }
}

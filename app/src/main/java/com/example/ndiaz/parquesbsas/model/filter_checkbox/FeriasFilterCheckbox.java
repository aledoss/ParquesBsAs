package com.example.ndiaz.parquesbsas.model.filter_checkbox;

import com.example.ndiaz.parquesbsas.model.Feria;

import java.util.List;

public class FeriasFilterCheckbox implements BaseFilterCheckbox {

    private List<Feria> ferias;

    public FeriasFilterCheckbox(List<Feria> ferias) {
        this.ferias = ferias;
    }

    @Override
    public String getTitle(int position) {
        return ferias.get(position).getTipo();
    }

    @Override
    public int getId(int position) {
        return ferias.get(position).getIdFeria();
    }

    @Override
    public int getCount() {
        return ferias.size();
    }
}

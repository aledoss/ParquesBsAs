package com.example.ndiaz.parquesbsas.model.filter_checkbox;

public interface BaseFilterCheckbox {

    String getTitle();

    int getId();

    boolean isChecked();

    void setChecked(boolean isChecked);

    Object getObject();
}

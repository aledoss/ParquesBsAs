package com.example.ndiaz.parquesbsas.model.ParqueComponentes;

import android.content.Intent;

import com.example.ndiaz.parquesbsas.model.ParqueComponente;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;
import com.example.ndiaz.parquesbsas.ui.activities.componentes_parque.ListaFeriasActivity;

import static com.example.ndiaz.parquesbsas.constants.Constants.ID_PARQUE;

class ParqueComponenteFeria extends ParqueComponente {

    @Override
    public void navigateToActivity(BaseActivity baseActivity) {
        Intent intent = new Intent(baseActivity, ListaFeriasActivity.class);
        intent.putExtra(ID_PARQUE, getIdParque());
        baseActivity.startActivity(intent);
    }

}

package com.example.ndiaz.parquesbsas.model.ParqueComponentes;

import android.content.Context;
import android.content.Intent;

import com.example.ndiaz.parquesbsas.model.ParqueComponente;
import com.example.ndiaz.parquesbsas.ui.activities.ListaReclamosParqueActivity;

import static com.example.ndiaz.parquesbsas.constants.Constants.ID_PARQUE;

class ParqueComponenteReclamo extends ParqueComponente {

    @Override
    public void navigateToActivity(Context context) {
        Intent intent = new Intent(context, ListaReclamosParqueActivity.class);
        intent.putExtra(ID_PARQUE, getIdParque());
        context.startActivity(intent);
    }
}

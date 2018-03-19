package com.example.ndiaz.parquesbsas.model.ParqueComponentes;

import android.content.Context;
import android.content.Intent;

import com.example.ndiaz.parquesbsas.model.ParqueComponente;
import com.example.ndiaz.parquesbsas.ui.activities.componentes_parque.ListaFeriasItinerantesActivity;

import static com.example.ndiaz.parquesbsas.constants.Constants.ID_PARQUE;

class ParqueComponenteFeriaItinerante extends ParqueComponente {

    @Override
    public void navigateToActivity(Context context) {
        Intent intent = new Intent(context, ListaFeriasItinerantesActivity.class);
        intent.putExtra(ID_PARQUE, getIdParque());
        context.startActivity(intent);
    }
}

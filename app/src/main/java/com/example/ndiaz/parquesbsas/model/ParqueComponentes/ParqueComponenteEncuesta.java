package com.example.ndiaz.parquesbsas.model.ParqueComponentes;

import android.content.Context;

import com.example.ndiaz.parquesbsas.model.ParqueComponente;

class ParqueComponenteEncuesta extends ParqueComponente{

    public ParqueComponenteEncuesta(ParqueComponente parqueComponente) {
        super.setParqueComponente(parqueComponente);
    }

    @Override
    public void navigateToActivity(Context context) {
        /*Intent intent = new Intent(context, ListaEncuestasParqueActivity.class);
        intent.putExtra(ID_PARQUE, getIdParque());
        context.startActivity(intent);*/
    }
}

package com.example.ndiaz.parquesbsas.listeners;

import com.example.ndiaz.parquesbsas.model.Calificacion;
import com.example.ndiaz.parquesbsas.model.Encuesta;

/**
 * Created by nicolas on 12/06/18.
 */

public interface OnCalificarEncuestaListener {

    void onCalificarEncuesta(Encuesta encuesta, Calificacion calificacion);

}

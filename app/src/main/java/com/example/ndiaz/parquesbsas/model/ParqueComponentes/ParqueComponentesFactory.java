package com.example.ndiaz.parquesbsas.model.ParqueComponentes;

import com.example.ndiaz.parquesbsas.model.ParqueComponente;

import java.util.ArrayList;
import java.util.List;

import static com.example.ndiaz.parquesbsas.constants.ParquesConstants.PARQUE_COMPONENTE_ACTIVIDAD;
import static com.example.ndiaz.parquesbsas.constants.ParquesConstants.PARQUE_COMPONENTE_ESTACION_SALUDABLE;
import static com.example.ndiaz.parquesbsas.constants.ParquesConstants.PARQUE_COMPONENTE_FERIA;
import static com.example.ndiaz.parquesbsas.constants.ParquesConstants.PARQUE_COMPONENTE_FERIA_ITINERANTE;
import static com.example.ndiaz.parquesbsas.constants.ParquesConstants.PARQUE_COMPONENTE_PUNTO_VERDE;

public class ParqueComponentesFactory {

    public ParqueComponentesFactory() {
    }

    public List<ParqueComponente> getParqueComponentes(List<ParqueComponente> response) {
        List<ParqueComponente> parqueComponentes = new ArrayList<>();

        for (ParqueComponente parqueComponente : response) {
            String nombreComponente = parqueComponente.getNombreComponente();
            switch (nombreComponente) {
                case PARQUE_COMPONENTE_ACTIVIDAD:
                    parqueComponentes.add(new ParqueComponenteActividad(parqueComponente));
                    break;
                case PARQUE_COMPONENTE_FERIA:
                    parqueComponentes.add(new ParqueComponenteFeria(parqueComponente));
                    break;
                case PARQUE_COMPONENTE_FERIA_ITINERANTE:
                    parqueComponentes.add(new ParqueComponenteFeriaItinerante(parqueComponente));
                    break;
                case PARQUE_COMPONENTE_ESTACION_SALUDABLE:
                    parqueComponentes.add(new ParqueComponenteEstacionSaludable(parqueComponente));
                    break;
                case PARQUE_COMPONENTE_PUNTO_VERDE:
                    parqueComponentes.add(new ParqueComponentePuntoVerde(parqueComponente));
                    break;
                default:
                    break;
            }
        }

        return parqueComponentes;
    }
}

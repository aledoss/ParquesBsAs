package com.example.ndiaz.parquesbsas.model.ParqueComponentes;

import com.example.ndiaz.parquesbsas.model.ParqueComponente;

import java.util.ArrayList;
import java.util.List;

import static com.example.ndiaz.parquesbsas.constants.ParquesConstants.PARQUE_COMPONENTE_ACTIVIDAD;
import static com.example.ndiaz.parquesbsas.constants.ParquesConstants.PARQUE_COMPONENTE_DESC_GENERAL;
import static com.example.ndiaz.parquesbsas.constants.ParquesConstants.PARQUE_COMPONENTE_ENCUESTA;
import static com.example.ndiaz.parquesbsas.constants.ParquesConstants.PARQUE_COMPONENTE_ESTACION_SALUDABLE;
import static com.example.ndiaz.parquesbsas.constants.ParquesConstants.PARQUE_COMPONENTE_FERIA;
import static com.example.ndiaz.parquesbsas.constants.ParquesConstants.PARQUE_COMPONENTE_FERIA_ITINERANTE;
import static com.example.ndiaz.parquesbsas.constants.ParquesConstants.PARQUE_COMPONENTE_PUNTO_VERDE;
import static com.example.ndiaz.parquesbsas.constants.ParquesConstants.PARQUE_COMPONENTE_RECLAMO;

public class ParqueComponentesFactory {

    public List<ParqueComponente> getParqueComponentes(List<ParqueComponente> response, int idParque) {
        List<ParqueComponente> parqueComponentes = new ArrayList<>();
        parqueComponentes.add(crearParqueComponenteDescGeneral(idParque));

        for (int i = 0; i < response.size(); i++) {
            ParqueComponente parqueComponente = response.get(i);
            String nombreComponente = parqueComponente.getNombreComponente();

            switch (nombreComponente) {
                case PARQUE_COMPONENTE_ACTIVIDAD:
                    parqueComponentes.add(new ParqueComponenteActividad());
                    break;
                case PARQUE_COMPONENTE_FERIA:
                    parqueComponentes.add(new ParqueComponenteFeria());
                    break;
                case PARQUE_COMPONENTE_FERIA_ITINERANTE:
                    parqueComponentes.add(new ParqueComponenteFeriaItinerante());
                    break;
                case PARQUE_COMPONENTE_ESTACION_SALUDABLE:
                    parqueComponentes.add(new ParqueComponenteEstacionSaludable());
                    break;
                case PARQUE_COMPONENTE_PUNTO_VERDE:
                    parqueComponentes.add(new ParqueComponentePuntoVerde());
                    break;
                default:
                    break;
            }
            parqueComponentes.get(i+1).setIdParque(idParque);
            parqueComponentes.get(i+1).setNombreComponente(parqueComponente.getNombreComponente());
        }

        parqueComponentes.add(crearParqueComponenteReclamo(idParque));
        parqueComponentes.add(crearParqueComponenteEncuesta(idParque));

        return parqueComponentes;
    }

    private ParqueComponente crearParqueComponenteDescGeneral(int idParque) {
        ParqueComponente parqueComponente = new ParqueComponenteDescGeneral();
        parqueComponente.setIdParque(idParque);
        parqueComponente.setNombreComponente(PARQUE_COMPONENTE_DESC_GENERAL);
        return parqueComponente;
    }

    private ParqueComponente crearParqueComponenteReclamo(int idParque) {
        ParqueComponente parqueComponente = new ParqueComponenteReclamo();
        parqueComponente.setIdParque(idParque);
        parqueComponente.setNombreComponente(PARQUE_COMPONENTE_RECLAMO);
        return parqueComponente;
    }

    private ParqueComponente crearParqueComponenteEncuesta(int idParque) {
        ParqueComponente parqueComponente = new ParqueComponenteEncuesta();
        parqueComponente.setIdParque(idParque);
        parqueComponente.setNombreComponente(PARQUE_COMPONENTE_ENCUESTA);
        return parqueComponente;
    }
}

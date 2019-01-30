package com.example.ndiaz.parquesbsas.listeners;

import com.example.ndiaz.parquesbsas.model.ReclamoFecha;

public interface OnReclamoListenerClick {
    void onDelete(int idReclamo);

    void onClick(ReclamoFecha reclamoFecha);
}

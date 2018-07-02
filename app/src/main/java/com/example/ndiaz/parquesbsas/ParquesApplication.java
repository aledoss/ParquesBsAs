package com.example.ndiaz.parquesbsas;

import android.app.Application;

import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.Usuario;
import com.facebook.stetho.Stetho;

public class ParquesApplication extends Application {

    private static ParquesApplication instance;
    private Usuario user;
    private Parque parque;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
        // TODO: 02/07/2018 Sacara mock
        setUser(new Usuario("Juan", "Perez", "36109123", 1,
                "DNI", "juan.perez@hotmail.com", "123"));
    }

    public static ParquesApplication getInstance() {
        return instance;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Parque getParque() {
        return parque;
    }

    public void setParque(Parque parque) {
        this.parque = parque;
    }
}

package com.example.ndiaz.parquesbsas;

import android.app.Application;

import com.example.ndiaz.parquesbsas.model.Usuario;

public class ParquesApplication extends Application{

    private static ParquesApplication instance;
    private Usuario user;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
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
}
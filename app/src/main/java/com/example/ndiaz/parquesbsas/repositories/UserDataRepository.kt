package com.example.ndiaz.parquesbsas.repositories

import com.example.ndiaz.parquesbsas.model.Usuario
import com.example.ndiaz.parquesbsas.preferences.DefaultPreferencesRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class UserDataRepository(val prefs: DefaultPreferencesRepository) {

    fun saveUserData(usuario: Usuario) {
        prefs.userEmail = usuario.email
        prefs.userPassword = usuario.password
    }

    fun getUserData(): Single<Usuario> {
        return Single.zip(
                userEmail,
                userPassword,
                BiFunction { t1: String, t2: String -> Usuario(t1, t2) }
        )
    }

    private val userEmail: Single<String>
        get() = Single.fromCallable { prefs.userEmail }

    private val userPassword: Single<String>
        get() = Single.fromCallable { prefs.userPassword }


}
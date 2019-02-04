package com.example.ndiaz.parquesbsas.repositories

import com.example.ndiaz.parquesbsas.helpers.CipherWrapper
import com.example.ndiaz.parquesbsas.model.Usuario
import com.example.ndiaz.parquesbsas.preferences.DefaultPreferencesRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class UserDataRepository(val prefs: DefaultPreferencesRepository, val cypher: CipherWrapper) {

    fun saveUserData(usuario: Usuario) {
        prefs.userEmail = cypher.encrypt(usuario.email)
        prefs.userPassword = cypher.encrypt(usuario.password)
    }

    fun getUserData(): Single<Usuario> {
        return Single.zip(
                userEmail,
                userPassword,
                BiFunction { t1: String, t2: String -> Usuario(t1, t2) }
        )
    }

    private val userEmail: Single<String>
        get() = Single.fromCallable {
            val userEmail = prefs.userEmail
            if (userEmail.isEmpty()) {
                userEmail
            } else {
                cypher.decrypt(userEmail)
            }
        }

    private val userPassword: Single<String>
        get() = Single.fromCallable {
            val userPassword = prefs.userPassword
            if (userPassword.isEmpty()) {
                userPassword
            } else {
                cypher.decrypt(userPassword)
            }
        }


}
package com.example.ndiaz.parquesbsas.helpers

import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class CipherWrapper {

    companion object {
        val SHA256 = "SHA-256"
        val AES = "AES"
    }

//    val cipher: Cipher = Cipher.getInstance()

    /*fun encrypt(data: String, key: Key?): String {
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val bytes = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun decrypt(data: String, key: Key?): String {
        cipher.init(Cipher.DECRYPT_MODE, key)
        val encryptedData = Base64.decode(data, Base64.DEFAULT)
        val decodedData = cipher.doFinal(encryptedData)
        return String(decodedData)
    }*/

    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(AES)
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        val bytes = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun decrypt(data: String): String {
        val cipher = Cipher.getInstance(AES)
        cipher.init(Cipher.DECRYPT_MODE, getKey())
        val encryptedData = Base64.decode(data, Base64.DEFAULT)
        val decodedData = cipher.doFinal(encryptedData)
        return String(decodedData)
    }

    private fun getKey(): SecretKeySpec {
        val msgDigest = MessageDigest.getInstance(SHA256);
        val bytes: ByteArray = "passtest".toByteArray(Charsets.UTF_8)
        msgDigest.update(bytes, 0, bytes.size)
        val key = msgDigest.digest()
        return SecretKeySpec(key, AES)
    }
}
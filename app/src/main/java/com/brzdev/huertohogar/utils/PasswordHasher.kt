package com.brzdev.huertohogar.utils

import java.security.MessageDigest

object PasswordHasher {

    // Convierte un string (contraseña) a un hash SHA-256
    fun hash(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256")
            .digest(password.toByteArray())
        return bytesToHex(bytes)
    }

    // Función de ayuda para convertir el array de bytes a un string hexadecimal
    private fun bytesToHex(bytes: ByteArray): String {
        val builder = StringBuilder()
        for (byte in bytes) {
            builder.append(String.format("%02x", byte))
        }
        return builder.toString()
    }
}
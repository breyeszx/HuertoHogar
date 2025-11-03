package com.brzdev.huertohogar.utils

import java.security.MessageDigest

object PasswordHasher {


    fun hash(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256")
            .digest(password.toByteArray())
        return bytesToHex(bytes)
    }


    private fun bytesToHex(bytes: ByteArray): String {
        val builder = StringBuilder()
        for (byte in bytes) {
            builder.append(String.format("%02x", byte))
        }
        return builder.toString()
    }
}
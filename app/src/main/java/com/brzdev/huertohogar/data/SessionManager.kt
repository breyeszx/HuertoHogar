package com.brzdev.huertohogar.data


import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences
    private val PREFS_NAME = "HuertoHogarPrefs"
    private val KEY_USER_ID = "user_id"

    init {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Guarda el ID del usuario que inició sesión
    fun saveSession(uid: Int) {
        val editor = prefs.edit()
        editor.putInt(KEY_USER_ID, uid)
        editor.apply()
    }

    // Obtiene el ID del usuario actual. Devuelve -1 si nadie ha iniciado sesión.
    fun getUserId(): Int {
        return prefs.getInt(KEY_USER_ID, -1)
    }

    // Cierra la sesión
    fun clearSession() {
        val editor = prefs.edit()
        editor.remove(KEY_USER_ID)
        editor.apply()
    }
}
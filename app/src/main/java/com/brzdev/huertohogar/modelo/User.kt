package com.brzdev.huertohogar.modelo


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    // Asegura que no puedan existir dos usuarios con el mismo email
    indices = [Index(value = ["email"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,

    val email: String,

    // NUNCA guardaremos la contraseña, solo su "hash"
    val hashedPassword: String,

    val address: String = "",
    val phone: String = ""
)
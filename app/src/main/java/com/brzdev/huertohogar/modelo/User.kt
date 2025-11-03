package com.brzdev.huertohogar.modelo


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [Index(value = ["email"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,

    val email: String,

    val hashedPassword: String,

    val address: String = "",
    val phone: String = ""
)
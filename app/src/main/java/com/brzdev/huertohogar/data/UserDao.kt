package com.brzdev.huertohogar.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.brzdev.huertohogar.modelo.User

@Dao
interface UserDao {
    // Inserta un nuevo usuario
    @Insert
    suspend fun insertUser(user: User)

    // Busca un usuario por su email (para el login)
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    // Busca un usuario por su ID (para sesiones)
    @Query("SELECT * FROM users WHERE uid = :uid LIMIT 1")
    suspend fun getUserById(uid: Int): User?

    // Actualiza el perfil
    @Update
    suspend fun updateUser(user: User)
}
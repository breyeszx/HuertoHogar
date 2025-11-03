package com.brzdev.huertohogar.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.brzdev.huertohogar.modelo.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)


    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE uid = :uid LIMIT 1")
    suspend fun getUserById(uid: Int): User?

    @Update
    suspend fun updateUser(user: User)
}
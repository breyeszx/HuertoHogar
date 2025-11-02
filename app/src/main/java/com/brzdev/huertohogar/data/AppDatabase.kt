package com.brzdev.huertohogar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.brzdev.huertohogar.modelo.Order // IMPORTA
import com.brzdev.huertohogar.modelo.OrderDetail // IMPORTA
import com.brzdev.huertohogar.modelo.User

// AÑADE Order y OrderDetail a la lista de entidades
@Database(entities = [User::class, Order::class, OrderDetail::class], version = 2, exportSchema = false) // SUBE LA VERSIÓN A 2
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao // AÑADE EL NUEVO DAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "huertohogar_database"
                )
                    // Le decimos a Room que destruya y recree la base de datos si las versiones no coinciden.
                    // Esto es perfecto para desarrollo, pero en producción requeriría un plan de migración.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
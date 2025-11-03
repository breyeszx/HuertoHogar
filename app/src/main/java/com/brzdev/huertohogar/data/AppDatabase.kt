package com.brzdev.huertohogar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.brzdev.huertohogar.modelo.Order
import com.brzdev.huertohogar.modelo.OrderDetail
import com.brzdev.huertohogar.modelo.User


@Database(entities = [User::class, Order::class, OrderDetail::class], version = 2, exportSchema = false) // SUBE LA VERSIÓN A 2
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao

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
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
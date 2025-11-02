package com.brzdev.huertohogar.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val userOwnerId: Int, // El ID del usuario que hizo la orden
    val date: Long, // La fecha de la orden
    val total: Double,
    val shippingAddress: String // La dirección de envío guardada
)
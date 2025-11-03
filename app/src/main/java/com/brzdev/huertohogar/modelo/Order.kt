package com.brzdev.huertohogar.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val userOwnerId: Int,
    val date: Long,
    val total: Double,
    val shippingAddress: String
)
package com.brzdev.huertohogar.modelo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_details",
    // Define la relación entre OrderDetail y Order
    foreignKeys = [
        ForeignKey(
            entity = Order::class,
            parentColumns = ["orderId"],
            childColumns = ["orderOwnerId"],
            onDelete = ForeignKey.CASCADE // Si se borra una orden, se borran sus detalles
        )
    ]
)
data class OrderDetail(
    @PrimaryKey(autoGenerate = true)
    val detailId: Int = 0,
    val orderOwnerId: Int, // El ID de la orden a la que pertenece
    val productName: String,
    val quantity: Int,
    val pricePerUnit: Double
)
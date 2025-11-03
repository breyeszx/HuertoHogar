package com.brzdev.huertohogar.modelo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_details",
    foreignKeys = [
        ForeignKey(
            entity = Order::class,
            parentColumns = ["orderId"],
            childColumns = ["orderOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OrderDetail(
    @PrimaryKey(autoGenerate = true)
    val detailId: Int = 0,
    val orderOwnerId: Int,
    val productName: String,
    val quantity: Int,
    val pricePerUnit: Double
)
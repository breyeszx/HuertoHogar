package com.brzdev.huertohogar.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.brzdev.huertohogar.modelo.Order
import com.brzdev.huertohogar.modelo.OrderDetail

@Dao
interface OrderDao {

    @Insert
    suspend fun insertOrder(order: Order): Long

    @Insert
    suspend fun insertOrderDetails(details: List<OrderDetail>)

    @Transaction
    suspend fun insertOrderWithDetails(order: Order, details: List<OrderDetail>) {
        val orderId = insertOrder(order)
        val detailsWithOrderId = details.map { it.copy(orderOwnerId = orderId.toInt()) }
        insertOrderDetails(detailsWithOrderId)
    }

    @Query("SELECT * FROM orders WHERE userOwnerId = :userId ORDER BY date DESC")
    suspend fun getOrdersByUser(userId: Int): List<Order>
}
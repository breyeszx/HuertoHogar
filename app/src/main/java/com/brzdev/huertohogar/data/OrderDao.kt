package com.brzdev.huertohogar.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.brzdev.huertohogar.modelo.Order
import com.brzdev.huertohogar.modelo.OrderDetail

@Dao
interface OrderDao {

    // Inserta una orden y devuelve su ID (Long)
    @Insert
    suspend fun insertOrder(order: Order): Long

    // Inserta una lista de detalles de la orden
    @Insert
    suspend fun insertOrderDetails(details: List<OrderDetail>)

    // Esta es una transacción. Asegura que ambas cosas (insertar orden
    // y sus detalles) ocurran exitosamente, o ninguna lo haga.
    @Transaction
    suspend fun insertOrderWithDetails(order: Order, details: List<OrderDetail>) {
        val orderId = insertOrder(order) // Obtiene el ID de la nueva orden

        // Asigna el ID de la orden a cada detalle
        val detailsWithOrderId = details.map { it.copy(orderOwnerId = orderId.toInt()) }

        insertOrderDetails(detailsWithOrderId)
    }
}
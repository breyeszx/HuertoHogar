package com.brzdev.huertohogar.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.brzdev.huertohogar.data.AppDatabase
import com.brzdev.huertohogar.modelo.Order
import com.brzdev.huertohogar.modelo.OrderDetail
import com.brzdev.huertohogar.modelo.User
import kotlinx.coroutines.launch

class CheckoutViewModel(application: Application) : AndroidViewModel(application) {

    private val orderDao = AppDatabase.getDatabase(application).orderDao()

    fun placeOrder(
        user: User,
        cartItems: List<CartItem>,
        total: Double,
        context: Context,
        onOrderPlaced: () -> Unit // Callback para ejecutar cuando la orden esté lista
    ) {
        viewModelScope.launch {
            // 1. Verifica si el usuario tiene una dirección
            if (user.address.isBlank()) {
                Toast.makeText(context, "Por favor, añade una dirección en 'Mi Perfil' antes de pagar", Toast.LENGTH_LONG).show()
                return@launch
            }

            // 2. Crear el objeto Orden
            val newOrder = Order(
                userOwnerId = user.uid,
                date = System.currentTimeMillis(), // Fecha actual
                total = total,
                shippingAddress = user.address
            )

            // 3. Crear los detalles de la orden
            val orderDetails = cartItems.map { cartItem ->
                OrderDetail(
                    // orderOwnerId se asignará en la transacción del DAO
                    orderOwnerId = 0,
                    productName = cartItem.product.name,
                    quantity = cartItem.quantity,
                    pricePerUnit = cartItem.product.price
                )
            }

            // 4. Guardar en la base de datos
            orderDao.insertOrderWithDetails(newOrder, orderDetails)

            // 5. Mostrar éxito y ejecutar el callback
            Toast.makeText(context, "¡Pedido realizado con éxito!", Toast.LENGTH_SHORT).show()
            onOrderPlaced()
        }
    }
}
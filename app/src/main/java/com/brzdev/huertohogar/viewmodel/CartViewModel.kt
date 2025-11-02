package com.brzdev.huertohogar.viewmodel

import androidx.lifecycle.ViewModel
import com.brzdev.huertohogar.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CartItem(val product: Product, val quantity: Int)

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addToCart(product: Product) {
        _cartItems.update { currentList ->
            val existingItem = currentList.find { it.product.id == product.id }
            if (existingItem != null) {
                // Si el ítem existe, actualiza su cantidad
                currentList.map {
                    if (it.product.id == product.id) it.copy(quantity = it.quantity + 1) else it
                }
            } else {
                // Si es un ítem nuevo, añádelo a la lista
                currentList + CartItem(product = product, quantity = 1)
            }
        }
    }

    // --- NUEVAS FUNCIONES AÑADIDAS ---

    fun increaseQuantity(productId: String) {
        _cartItems.update { currentList ->
            currentList.map {
                if (it.product.id == productId) it.copy(quantity = it.quantity + 1) else it
            }
        }
    }

    fun decreaseQuantity(productId: String) {
        _cartItems.update { currentList ->
            // mapNotNull elimina el ítem de la lista si la función devuelve null
            currentList.mapNotNull {
                if (it.product.id == productId) {
                    if (it.quantity > 1) it.copy(quantity = it.quantity - 1) else null // Elimina si la cantidad es 0
                } else {
                    it
                }
            }
        }
    }

    fun removeFromCart(productId: String) {
        _cartItems.update { currentList ->
            currentList.filterNot { it.product.id == productId }
        }
    }
    fun clearCart() {
        _cartItems.update { emptyList() }
    }
}
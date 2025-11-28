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

    fun addToCart(product: Product, quantityToAdd: Int) {
        _cartItems.update { currentList ->
            val existingItem = currentList.find { it.product.id == product.id }
            if (existingItem != null) {
                currentList.map {
                    if (it.product.id == product.id) it.copy(quantity = it.quantity + quantityToAdd) else it
                }
            } else {
                currentList + CartItem(product = product, quantity = quantityToAdd)
            }
        }
    }

    fun increaseQuantity(productId: String) {
        _cartItems.update { currentList ->
            currentList.map {
                if (it.product.id == productId) it.copy(quantity = it.quantity + 1) else it
            }
        }
    }

    fun decreaseQuantity(productId: String) {
        _cartItems.update { currentList ->
            currentList.mapNotNull {
                if (it.product.id == productId) {
                    if (it.quantity > 1) it.copy(quantity = it.quantity - 1) else null
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
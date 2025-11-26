package com.brzdev.huertohogar.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.brzdev.huertohogar.data.AppDatabase
import com.brzdev.huertohogar.data.SessionManager
import com.brzdev.huertohogar.modelo.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val orderDao = AppDatabase.getDatabase(application).orderDao()
    private val sessionManager = SessionManager(application)

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    init {
        loadOrders()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            val userId = sessionManager.getUserId()
            if (userId != -1) {
                _orders.value = orderDao.getOrdersByUser(userId)
            }
        }
    }
}
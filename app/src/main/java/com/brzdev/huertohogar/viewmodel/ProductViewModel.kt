package com.brzdev.huertohogar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brzdev.huertohogar.data.DataSource
import com.brzdev.huertohogar.model.Product
import kotlinx.coroutines.flow.*

class ProductViewModel : ViewModel() {
    private val allProducts = DataSource.products

    val categories: List<String> = listOf("Todos") + DataSource.categories.map { it.name }

    private val _selectedCategory = MutableStateFlow("Todos")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // --- NUEVO ESTADO PARA LA BÚSQUEDA ---
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Combinamos category, searchQuery y la lista original para filtrar
    val filteredProducts: StateFlow<List<Product>> = combine(
        _selectedCategory,
        _searchQuery
    ) { category, query ->
        var products = allProducts

        // 1. Filtro por Categoría
        if (category != "Todos") {
            products = products.filter { it.category == category }
        }

        // 2. Filtro por Texto (Nombre)
        if (query.isNotEmpty()) {
            products = products.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }

        products
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = allProducts
    )

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    // --- NUEVA FUNCIÓN PARA ACTUALIZAR BÚSQUEDA ---
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}
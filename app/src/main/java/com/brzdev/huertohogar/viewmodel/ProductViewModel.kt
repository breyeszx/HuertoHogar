package com.brzdev.huertohogar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brzdev.huertohogar.data.DataSource
import com.brzdev.huertohogar.model.Product
import kotlinx.coroutines.flow.*

class ProductViewModel : ViewModel() {
    private val allProducts = DataSource.products

    // Obtiene los nombres de las categorías y añade "Todos" al principio
    val categories: List<String> = listOf("Todos") + DataSource.categories.map { it.name }

    // Estado para la categoría seleccionada. El valor inicial es "Todos"
    private val _selectedCategory = MutableStateFlow("Todos")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Flujo de productos filtrados que reacciona a los cambios en _selectedCategory
    val filteredProducts: StateFlow<List<Product>> = _selectedCategory.map { category ->
        if (category == "Todos") {
            allProducts
        } else {
            // Filtra la lista de productos basada en el nombre de la categoría
            allProducts.filter { it.category == category }
        }
    }.stateIn(
        // Inicia el flujo y lo mantiene activo mientras la UI esté visible
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = allProducts // Muestra todos los productos al inicio
    )

    // Función para que la UI actualice la categoría seleccionada
    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }
}
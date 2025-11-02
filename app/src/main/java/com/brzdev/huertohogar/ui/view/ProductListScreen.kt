package com.brzdev.huertohogar.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brzdev.huertohogar.ui.CategoryFilterBar
import com.brzdev.huertohogar.viewmodel.ProductViewModel

@Composable
fun ProductListScreen(
    onProductClick: (String) -> Unit,
    // Crea una instancia del ViewModel asociada a esta pantalla
    productViewModel: ProductViewModel = viewModel()
) {
    // Observa los estados del ViewModel
    val products by productViewModel.filteredProducts.collectAsState()
    val categories = productViewModel.categories
    val selectedCategory by productViewModel.selectedCategory.collectAsState()

    Column {
        // 1. Añade la barra de filtros en la parte superior
        CategoryFilterBar(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { category ->
                productViewModel.selectCategory(category)
            }
        )

        // 2. Muestra la lista de productos (ahora filtrada)
        LazyColumn {
            items(products) { product ->
                ProductCard(
                    product = product,
                    onCardClick = { onProductClick(product.id) }
                )
            }
        }
    }
}
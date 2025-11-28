package com.brzdev.huertohogar.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.brzdev.huertohogar.ui.CategoryFilterBar
import com.brzdev.huertohogar.ui.ProductCard
import com.brzdev.huertohogar.viewmodel.ProductViewModel

@Composable
fun ProductListScreen(
    onProductClick: (String) -> Unit,
    productViewModel: ProductViewModel
) {
    val products by productViewModel.filteredProducts.collectAsState()
    val categories = productViewModel.categories
    val selectedCategory by productViewModel.selectedCategory.collectAsState()
    // Observamos el texto de búsqueda
    val searchQuery by productViewModel.searchQuery.collectAsState()

    Column {
        // --- BARRA DE BÚSQUEDA ---
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { productViewModel.onSearchQueryChanged(it) },
            label = { Text("Buscar productos...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true
        )

        CategoryFilterBar(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { category ->
                productViewModel.selectCategory(category)
            }
        )

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
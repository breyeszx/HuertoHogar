package com.brzdev.huertohogar.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
// ¡Elimina esta importación! import androidx.lifecycle.viewmodel.compose.viewModel
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

    Column {
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
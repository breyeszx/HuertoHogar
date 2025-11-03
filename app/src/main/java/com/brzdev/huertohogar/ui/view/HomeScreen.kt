package com.brzdev.huertohogar.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.brzdev.huertohogar.viewmodel.ProductViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ProductViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Bienvenido a HuertoHogar",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Selecciona una categoría para empezar",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        CategoryButton(
            text = "Frutas",
            onClick = {
                viewModel.selectCategory("Frutas Frescas")
                navController.navigate("productList")
            }
        )
        Spacer(modifier = Modifier.height(24.dp))

        CategoryButton(
            text = "Verduras",
            onClick = {
                viewModel.selectCategory("Verduras Orgánicas")
                navController.navigate("productList")
            }
        )
        Spacer(modifier = Modifier.height(24.dp))

        CategoryButton(
            text = "Todo",
            onClick = {
                viewModel.selectCategory("Todos")
                navController.navigate("productList")
            }
        )
    }
}

@Composable
fun CategoryButton(text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
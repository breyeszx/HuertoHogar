package com.brzdev.huertohogar.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.brzdev.huertohogar.data.api.WeatherApi
import com.brzdev.huertohogar.data.api.WeatherResponse
import com.brzdev.huertohogar.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ProductViewModel
) {
    val scope = rememberCoroutineScope()
    var weatherData by remember { mutableStateOf<WeatherResponse?>(null) }

    val apiKey = "ddf05c11c18177ff4c179a759543c894"

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = WeatherApi.create().getCurrentWeather(-33.4489, -70.6693, apiKey)
                weatherData = response
            } catch (e: Exception) {
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (weatherData != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = "https://openweathermap.org/img/wn/${weatherData!!.weather.first().icon}@2x.png",
                        contentDescription = "Icono del clima",
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "${weatherData!!.main.temp.toInt()}°C en ${weatherData!!.name}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = weatherData!!.weather.first().description.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Text(
            "Bienvenido a HuertoHogar",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
        Text(
            "Selecciona una categoría para empezar",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 24.dp, top = 8.dp)
        )

        CategoryButton("Frutas") {
            viewModel.selectCategory("Frutas Frescas")
            navController.navigate("productList")
        }
        Spacer(modifier = Modifier.height(16.dp))

        CategoryButton("Verduras") {
            viewModel.selectCategory("Verduras Orgánicas")
            navController.navigate("productList")
        }
        Spacer(modifier = Modifier.height(16.dp))

        CategoryButton("Todo") {
            viewModel.selectCategory("Todos")
            navController.navigate("productList")
        }
    }
}

@Composable
fun CategoryButton(text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = text, style = MaterialTheme.typography.titleLarge)
        }
    }
}
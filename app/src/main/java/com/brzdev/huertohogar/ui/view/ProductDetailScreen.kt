package com.brzdev.huertohogar.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.brzdev.huertohogar.data.DataSource
import com.brzdev.huertohogar.model.Product
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductDetailScreen(
    product: Product,
    onAddToCartClicked: () -> Unit // Callback para manejar el clic del botón
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Hace que la columna sea desplazable
    ) {
        AsyncImage(
            model = "https://picsum.photos/seed/${product.id}/600/400", // Imagen más grande
            contentDescription = "Imagen de ${product.name}",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
            Text(
                text = "${format.format(product.price)} por ${product.unit}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Descripción",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Disponibilidad",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${product.stock} ${product.unit.split(" ")[0]} en stock",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón para agregar al carrito
            Button(
                onClick = onAddToCartClicked,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("AGREGAR AL CARRITO")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    ProductDetailScreen(
        product = DataSource.products.first(),
        onAddToCartClicked = {} // Acción vacía para la previsualización
    )
}
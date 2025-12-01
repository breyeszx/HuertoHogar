package com.brzdev.huertohogar.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.brzdev.huertohogar.R
import com.brzdev.huertohogar.data.DataSource
import com.brzdev.huertohogar.model.Product
import com.brzdev.huertohogar.ui.theme.Montserrat
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit
) {
    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onCardClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Imagen de ${product.name}",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_launcher_background),
                placeholder = painterResource(id = R.drawable.ic_launcher_background)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontFamily = Montserrat,
                    color = Color.Black // <-- CAMBIO: Texto negro
                )
                Spacer(modifier = Modifier.height(4.dp))

                val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
                Text(
                    text = "${format.format(product.price)} por ${product.unit}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = Montserrat,
                    color = Color.Black // <-- CAMBIO: Texto negro
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Stock: ${product.stock} ${product.unit.split(" ")[0]}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = Montserrat,
                    color = Color.Black //
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    ProductCard(product = DataSource.products.first(), onCardClick = {})
}
package com.brzdev.huertohogar.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.brzdev.huertohogar.data.DataSource
import com.brzdev.huertohogar.model.Product
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class) // Required for Card onClick
@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit // Add this callback parameter
) {
    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onCardClick // Make the card clickable
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = "https://picsum.photos/seed/${product.id}/200", // Using a placeholder image service
                contentDescription = "Image of ${product.name}",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
                Text(
                    text = "${format.format(product.price)} por ${product.unit}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Stock: ${product.stock} ${product.unit.split(" ")[0]}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


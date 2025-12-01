package com.brzdev.huertohogar.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove // <-- AHORA SÍ LO USAMOS
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.brzdev.huertohogar.R
import com.brzdev.huertohogar.ui.theme.Montserrat
import com.brzdev.huertohogar.viewmodel.CartItem
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    val subtotal = cartItem.product.price * cartItem.quantity

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // --- IMAGEN (Con corrección para carga local) ---
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cartItem.product.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = cartItem.product.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background)
            )

            // --- INFORMACIÓN DE TEXTO ---
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cartItem.product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = format.format(subtotal),
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = Montserrat,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // --- BOTONES DE CANTIDAD ---
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Botón Disminuir (Menos)
                IconButton(onClick = onDecrease) {
                    Icon(
                        imageVector = Icons.Filled.Remove, // Icono nativo correcto
                        contentDescription = "Disminuir",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Cantidad
                Text(
                    text = "${cartItem.quantity}",
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                // Botón Aumentar (Más)
                IconButton(onClick = onIncrease) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Aumentar",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Botón Eliminar (Basurero)
                IconButton(onClick = onRemove) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
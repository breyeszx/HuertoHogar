package com.brzdev.huertohogar.ui.view


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brzdev.huertohogar.viewmodel.CartViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CartScreen(viewModel: CartViewModel,onCheckoutClick: () -> Unit) {


    val cartItems by viewModel.cartItems.collectAsState()

    val total = cartItems.sumOf { it.product.price * it.quantity }
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    if (cartItems.isEmpty()) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Tu carrito está vacío", style = MaterialTheme.typography.titleLarge)
        }
    } else {

        Column(Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.weight(1f), // Ocupa todo el espacio disponible
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(cartItems) { item ->
                    CartItemCard(
                        cartItem = item,
                        onIncrease = { viewModel.increaseQuantity(item.product.id) },
                        onDecrease = { viewModel.decreaseQuantity(item.product.id) },
                        onRemove = { viewModel.removeFromCart(item.product.id) }
                    )
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    "Total: ${format.format(total)}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onCheckoutClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("IR A PAGAR")
                }
            }
        }
    }
}
package com.brzdev.huertohogar.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brzdev.huertohogar.viewmodel.AuthViewModel
import com.brzdev.huertohogar.viewmodel.CartViewModel
import com.brzdev.huertohogar.viewmodel.CheckoutViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CheckoutScreen(
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel,
    checkoutViewModel: CheckoutViewModel = viewModel(),
    onOrderSuccess: () -> Unit
) {
    val user by authViewModel.currentUser.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val total = cartItems.sumOf { it.product.price * it.quantity }

    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Resumen del Pedido", style = MaterialTheme.typography.headlineLarge)

            Spacer(modifier = Modifier.height(16.dp))

            // 1. Detalles de Envío
            Text("Enviar a:", style = MaterialTheme.typography.titleLarge)
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(user?.email ?: "", fontWeight = FontWeight.Bold)
                    Text(user?.address ?: "Sin dirección. Añade una desde 'Mi Perfil'.")
                    Text(user?.phone ?: "Sin teléfono.")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Resumen de Ítems (solo el total)
            Text("Total Productos:", style = MaterialTheme.typography.titleLarge)
            Text("${cartItems.size} ítems", style = MaterialTheme.typography.bodyLarge)

        }

        // 3. Total y Botón de Pagar
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                "Total: ${format.format(total)}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = {
                    if (user != null) {
                        checkoutViewModel.placeOrder(
                            user = user!!,
                            cartItems = cartItems,
                            total = total,
                            context = context,
                            onOrderPlaced = {
                                cartViewModel.clearCart()
                                onOrderSuccess()
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("CONFIRMAR PEDIDO")
            }
        }
    }
}
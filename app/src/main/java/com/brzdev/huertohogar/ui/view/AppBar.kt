package com.brzdev.huertohogar.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp // <-- NUEVO ICONO
import androidx.compose.material.icons.filled.Person // <-- NUEVO ICONO
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HuertoHogarTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSignOutClick: () -> Unit,
    showCartIcon: Boolean = true,
    showProfileIcon: Boolean = true // <-- NUEVO PARÁMETRO
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
            }
        },
        // Aquí está el cambio principal:
        actions = {
            if (showCartIcon) {
                IconButton(onClick = onCartClick) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Carrito"
                    )
                }
            }
            if (showProfileIcon) {
                IconButton(onClick = onProfileClick) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Mi Perfil"
                    )
                }
            }
            IconButton(onClick = onSignOutClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Cerrar Sesión"
                )
            }
        }
    )
}
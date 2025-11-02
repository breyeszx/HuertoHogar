package com.brzdev.huertohogar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.brzdev.huertohogar.data.DataSource
import com.brzdev.huertohogar.ui.HuertoHogarTopAppBar
import com.brzdev.huertohogar.ui.ProductDetailScreen
import com.brzdev.huertohogar.ui.view.CartScreen
import com.brzdev.huertohogar.ui.view.CheckoutScreen
import com.brzdev.huertohogar.ui.view.LoginScreen
import com.brzdev.huertohogar.ui.view.ProductListScreen
import com.brzdev.huertohogar.ui.view.ProfileScreen
import com.brzdev.huertohogar.ui.view.SignUpScreen
import com.brzdev.huertohogar.viewmodel.AuthViewModel
import com.brzdev.huertohogar.viewmodel.AuthState
import com.brzdev.huertohogar.viewmodel.CartViewModel

@Composable
fun AppNavigation() {
    val authViewModel: AuthViewModel = viewModel()
    val authState by authViewModel.authState.collectAsState()

    when (authState) {
        AuthState.LOADING -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        AuthState.LOGGED_IN -> {
            MainAppNavigation(
                authViewModel = authViewModel,
                cartViewModel = viewModel()
            )
        }
        AuthState.LOGGED_OUT -> {
            AuthNavigation(authViewModel = authViewModel)
        }
    }
}

@Composable
fun AuthNavigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginClicked = { email, password ->
                    authViewModel.signIn(email, password, context)
                },
                onNavigateToSignUp = { navController.navigate("signup") }
            )
        }
        composable("signup") {
            SignUpScreen(
                onSignUpClicked = { email, password ->
                    authViewModel.signUp(email, password, context)
                },
                onNavigateToLogin = { navController.navigate("login") }
            )
        }
    }
}

// --- NAVEGACIÓN PRINCIPAL DE LA APP (Tienda, Carrito, etc.) ---
@Composable
fun MainAppNavigation(authViewModel: AuthViewModel, cartViewModel: CartViewModel) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val currentScreenTitle = when (currentRoute) {
        "productList" -> "Productos"
        "productDetail/{productId}" -> "Detalle del Producto"
        "cart" -> "Mi Carrito"
        "profile" -> "Mi Perfil"
        "checkout" -> "Finalizar Compra"
        "storeMap" -> "Nuestras Tiendas"
        else -> "Huerto Hogar"
    }

    // --- LÓGICA DE ICONOS ACTUALIZADA ---
    val routesToHideCart = listOf("cart", "profile", "checkout", "storeMap")
    val showCartIcon = currentRoute !in routesToHideCart
    val showProfileIcon = currentRoute != "profile"
    // --- FIN DE LÓGICA DE ICONOS ---

    Scaffold(
        topBar = {
            HuertoHogarTopAppBar(
                title = currentScreenTitle,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },

                // Pasa las acciones
                onCartClick = { navController.navigate("cart") },
                onProfileClick = { navController.navigate("profile") },
                onSignOutClick = { authViewModel.signOut() },

                // Pasa la lógica de visibilidad
                showCartIcon = showCartIcon,
                showProfileIcon = showProfileIcon
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "productList",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("productList") {
                ProductListScreen(
                    onProductClick = { productId ->
                        navController.navigate("productDetail/$productId")
                    }
                )
            }
            composable("productDetail/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")
                val product = DataSource.products.find { it.id == productId }
                if (product != null) {
                    ProductDetailScreen(
                        product = product,
                        onAddToCartClicked = { cartViewModel.addToCart(product) }
                    )
                }
            }
            composable("cart") {
                CartScreen(
                    viewModel = cartViewModel,
                    onCheckoutClick = { navController.navigate("checkout") }
                )
            }
            composable("checkout") {
                CheckoutScreen(
                    authViewModel = authViewModel,
                    cartViewModel = cartViewModel,
                    onOrderSuccess = {
                        navController.navigate("productList") {
                            popUpTo("productList") { inclusive = true }
                        }
                    }
                )
            }
            composable("profile") {
                ProfileScreen(authViewModel = authViewModel)
            }

        }
    }
}
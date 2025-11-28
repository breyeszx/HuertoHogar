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
import com.brzdev.huertohogar.ui.view.CartScreen
import com.brzdev.huertohogar.ui.view.CheckoutScreen
import com.brzdev.huertohogar.ui.view.HomeScreen
import com.brzdev.huertohogar.ui.view.LoginScreen
import com.brzdev.huertohogar.ui.view.OrderHistoryScreen
import com.brzdev.huertohogar.ui.view.ProductDetailScreen
import com.brzdev.huertohogar.ui.view.ProductListScreen
import com.brzdev.huertohogar.ui.view.ProfileScreen
import com.brzdev.huertohogar.ui.view.SignUpScreen
import com.brzdev.huertohogar.viewmodel.AuthViewModel
import com.brzdev.huertohogar.viewmodel.AuthState
import com.brzdev.huertohogar.viewmodel.CartViewModel
import com.brzdev.huertohogar.viewmodel.ProductViewModel

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

@Composable
fun MainAppNavigation(authViewModel: AuthViewModel, cartViewModel: CartViewModel) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val productViewModel: ProductViewModel = viewModel()

    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalItemsInCart = cartItems.sumOf { it.quantity }

    val currentScreenTitle = when (currentRoute) {
        "home" -> "Inicio"
        "productList" -> "Productos"
        "productDetail/{productId}" -> "Detalle del Producto"
        "cart" -> "Mi Carrito"
        "profile" -> "Mi Perfil"
        "checkout" -> "Finalizar Compra"
        "orderHistory" -> "Mis Pedidos"
        else -> "Huerto Hogar"
    }

    val routesToHideCart = listOf("cart", "profile", "checkout", "orderHistory")
    val showCartIcon = currentRoute !in routesToHideCart
    val showProfileIcon = currentRoute != "profile"

    Scaffold(
        topBar = {
            HuertoHogarTopAppBar(
                title = currentScreenTitle,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },

                onCartClick = { navController.navigate("cart") },
                onProfileClick = { navController.navigate("profile") },
                onSignOutClick = { authViewModel.signOut() },

                showCartIcon = showCartIcon,
                showProfileIcon = showProfileIcon,
                cartItemCount = totalItemsInCart
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("home") {
                HomeScreen(
                    navController = navController,
                    viewModel = productViewModel
                )
            }

            composable("productList") {
                ProductListScreen(
                    onProductClick = { productId ->
                        navController.navigate("productDetail/$productId")
                    },
                    productViewModel = productViewModel
                )
            }

            composable("productDetail/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")
                val product = DataSource.products.find { it.id == productId }
                if (product != null) {
                    ProductDetailScreen(
                        product = product,
                        onAddToCartClicked = { quantity ->
                            cartViewModel.addToCart(product, quantity)
                        }
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
                ProfileScreen(
                    authViewModel = authViewModel,
                    onMyOrdersClick = { navController.navigate("orderHistory") }
                )
            }

            composable("orderHistory") {
                OrderHistoryScreen()
            }
        }
    }
}
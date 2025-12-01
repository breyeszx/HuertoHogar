package com.brzdev.huertohogar.viewmodel

import com.brzdev.huertohogar.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    private lateinit var viewModel: CartViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val manzana = Product(
        id = "1", name = "Manzana", price = 1000.0, stock = 10,
        description = "", unit = "kg", category = "Fruta", imageUrl = ""
    )
    private val pera = Product(
        id = "2", name = "Pera", price = 1500.0, stock = 10,
        description = "", unit = "kg", category = "Fruta", imageUrl = ""
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CartViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addToCart agrega un nuevo item correctamente`() = runTest {
        viewModel.addToCart(manzana, 2)

        val cart = viewModel.cartItems.value
        assertEquals(1, cart.size)
        assertEquals("Manzana", cart[0].product.name)
        assertEquals(2, cart[0].quantity)
    }

    @Test
    fun `addToCart suma cantidad si el producto ya existe`() = runTest {
        viewModel.addToCart(manzana, 2)

        viewModel.addToCart(manzana, 3)

        // VERIFICACIÓN
        val cart = viewModel.cartItems.value
        assertEquals(1, cart.size)
        assertEquals(5, cart[0].quantity) // 2 + 3 = 5
    }

    @Test
    fun `calculo del total es correcto con multiples productos`() = runTest {
        viewModel.addToCart(manzana, 2)
        viewModel.addToCart(pera, 1)

        val cart = viewModel.cartItems.value
        val total = cart.sumOf { it.product.price * it.quantity }

        assertEquals(3500.0, total, 0.0)
    }

    @Test
    fun `removeFromCart elimina el producto del carrito`() = runTest {
        viewModel.addToCart(manzana, 1)
        viewModel.addToCart(pera, 1)
        viewModel.removeFromCart(manzana.id)

        val cart = viewModel.cartItems.value
        assertEquals(1, cart.size)
        assertEquals("Pera", cart[0].product.name) // Solo debe quedar la Pera
    }

    @Test
    fun `clearCart vacia el carrito completamente`() = runTest {
        viewModel.addToCart(manzana, 5)

        viewModel.clearCart()

        assertTrue(viewModel.cartItems.value.isEmpty())
    }
}
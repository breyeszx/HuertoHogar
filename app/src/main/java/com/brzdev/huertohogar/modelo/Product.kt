package com.brzdev.huertohogar.model

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val stock: Int,
    val description: String,
    val unit: String, // e.g., "kilo", "bolsa de 500g", "frasco de 500g"
    val category: String,
    val imageUrl: String // To store a link to the product image
)
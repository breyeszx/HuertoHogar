package com.brzdev.huertohogar.model

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val stock: Int,
    val description: String,
    val unit: String,
    val category: String,
    val imageUrl: String)
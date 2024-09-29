package com.example.inventorymanager.home.model

data class Product(
    val id: String = "",
    val productName: String = "",
    val stock: Int = 0,
    val unit: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val imageUrl: String = ""
)

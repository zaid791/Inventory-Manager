package com.example.inventorymanager.home.model

data class InventoryItem(
    val name: String,
    val stock: String,
    val category: String,
    val price: String,
    val imageResId: Int
)

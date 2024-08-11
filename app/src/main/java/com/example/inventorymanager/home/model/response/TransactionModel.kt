package com.example.inventorymanager.home.model.response

data class TransactionModel(
    val id: Long,
    val personName: String,
    val contactNumber: Long,
    val amountPaid: Long,
    val amountPending: Long,
    val address: String
)

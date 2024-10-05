package com.example.inventorymanager.home.model

import com.example.inventorymanager.common.UnitEnum
import java.time.LocalDateTime

data class TransactionModel(
    val transactionId: Long,
    val amountPaid: Long,
    val amountPending: Long,
    val dateTime: LocalDateTime,
    val items: List<Items>
)

data class Items(
    val name: String,
    val unitPrice: Long,
    val unitName: UnitEnum,
    val quantity: Long,
    val totalPrice: Long,
    val amountPaid: Long,
    val amountPending: Long
)

package com.example.inventorymanager.details.model.dataClass

import com.example.inventorymanager.common.Messages
import com.example.inventorymanager.home.model.response.TransactionModel

data class UserDetailsModel(
    val id: String? = Messages.BLANK,
    val firstName: String,
    val lastName: String,
    val companyName: String,
    val alias: String?,
    val mobileNumber: Int,
    val address: String,
    val transactions: List<TransactionModel>
) {
    // No-argument constructor required by Firestore
    @Suppress("unused")
    constructor() : this(
        null,
        Messages.BLANK,
        Messages.BLANK,
        Messages.BLANK,
        null,
        1234567890,
        Messages.BLANK,
        listOf()
    )
}

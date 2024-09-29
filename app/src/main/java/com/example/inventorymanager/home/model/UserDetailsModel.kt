package com.example.inventorymanager.home.model

import com.example.inventorymanager.common.Messages

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

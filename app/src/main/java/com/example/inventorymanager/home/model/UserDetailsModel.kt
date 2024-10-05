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
    val transactions: List<TransactionModel>,
    val totalAmountPaidAllTransactions: Long = 0,
    val totalAmountPendingAllTransactions: Long = 0,
    val documentId: String? = Messages.BLANK
) {
    // No-argument constructor required by Firestore
    @Suppress("unused")
    constructor() : this(
        Messages.BLANK,
        Messages.BLANK,
        Messages.BLANK,
        Messages.BLANK,
        null,
        1234567890,
        Messages.BLANK,
        listOf(),
        0,
        0,
        null
    )
}

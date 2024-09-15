package com.example.inventorymanager.details.model.dataClass

import com.example.inventorymanager.common.Messages

data class UserDetailsModel(
    val id: String? = Messages.BLANK,
    val firstName: String,
    val lastName: String,
    val companyName: String,
    val alias: String?,
    val mobileNumber: Int,
    val address: String
) {
    // No-argument constructor required by Firestore
    constructor() : this(null, Messages.BLANK, Messages.BLANK, Messages.BLANK, null, 123456789, Messages.BLANK)
}

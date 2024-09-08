package com.example.inventorymanager.details.model.dataClass

data class UserDetailsModel(
    val id: Int?,
    val firstName: String,
    val lastName: String,
    val companyName: String,
    val alias: String?,
    val mobileNumber: Int,
    val address: String
)

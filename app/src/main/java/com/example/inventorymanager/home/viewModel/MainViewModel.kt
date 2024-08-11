package com.example.inventorymanager.home.viewModel

import androidx.lifecycle.ViewModel
import com.example.inventorymanager.home.model.response.TransactionModel

class MainViewModel : ViewModel() {
    val list = mutableListOf(
        TransactionModel(
            id = 1,
            personName = "Bruce Wayne",
            amountPaid = 5000,
            amountPending = 10000,
            contactNumber = 123456789,
            address = "Wayne Tower"
        ),
        TransactionModel(
            id = 2,
            personName = "Clark Kent",
            amountPaid = 7000,
            amountPending = 80000,
            contactNumber = 1111111111,
            address = "Kent's Farm"
        ),
        TransactionModel(
            id = 3,
            personName = "Tony Stark",
            amountPaid = 500000,
            amountPending = 0,
            contactNumber = 222222222,
            address = "Stark Tower"
        ),
        TransactionModel(
            id = 4,
            personName = "Bruce Banner",
            amountPaid = 0,
            amountPending = 50000,
            contactNumber = 5876456464,
            address = "Bandra, Mumbai"
        ),
        TransactionModel(
            id = 5,
            personName = "Wade Wilson",
            amountPaid = 5000,
            amountPending = 10000,
            contactNumber = 67941322258,
            address = "Toronto, Canada"
        )
    )
}
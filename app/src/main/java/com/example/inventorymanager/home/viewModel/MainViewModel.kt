package com.example.inventorymanager.home.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.inventorymanager.common.FirestoreConstants
import com.example.inventorymanager.details.model.dataClass.UserDetailsModel
import com.example.inventorymanager.home.model.response.TransactionModel
import com.google.firebase.firestore.FirebaseFirestore

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
    fun fetchUserDetails(onResult: (List<UserDetailsModel>) -> Unit) {
        // Firestore instance
        val db = FirebaseFirestore.getInstance()

        // List to hold user details
        val userList = mutableListOf<UserDetailsModel>()

        // Fetch all documents from the 'users' collection
        db.collection(FirestoreConstants.COLLECTION_PEOPLE)
            .get()
            .addOnSuccessListener { documents ->
                // Iterate over all documents
                for (document in documents) {
                    // Map Firestore document to UserDetailsModel
                    val user = document.toObject(UserDetailsModel::class.java).copy(id = document.id)
                    // Add to list
                    userList.add(user)
                }
                // Return the list via callback
                onResult(userList)
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error fetching users", e)
                // Return empty list in case of error
                onResult(emptyList())
            }
    }

}
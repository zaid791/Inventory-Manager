package com.example.inventorymanager.details.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.inventorymanager.common.FirestoreConstants
import com.example.inventorymanager.common.Messages
import com.example.inventorymanager.details.model.dataClass.UserDetailsModel
import com.google.firebase.firestore.FirebaseFirestore

class DetailsViewModel : ViewModel() {
    fun addPerson(userDetails: UserDetailsModel, onResult: (Boolean) -> Unit) {
        // Firestore instance
        val db = FirebaseFirestore.getInstance()
        // Add a new document with auto-generated ID
        db.collection(FirestoreConstants.COLLECTION_PEOPLE)
            .add(userDetails)
            .addOnSuccessListener {
                // Operation was successful, return true
                onResult(true)
            }
            .addOnFailureListener { e ->
                // Log the error and return false
                Log.e(Messages.FIRESTORE_ERROR, Messages.ERROR_ADDING_DOCUMENT, e)
                onResult(false)
            }
    }
}
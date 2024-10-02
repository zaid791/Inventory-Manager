package com.example.inventorymanager.home.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.inventorymanager.common.Messages
import com.example.inventorymanager.home.model.UserDetailsModel
import com.google.firebase.firestore.FirebaseFirestore

class MainViewModel : ViewModel() {

    fun fetchUserDetails(collectionName: String, onResult: (List<UserDetailsModel>?) -> Unit) {
        // Firestore instance
        val db = FirebaseFirestore.getInstance()
        // List to hold user details
        val userList = mutableListOf<UserDetailsModel>()
        // Fetch all documents from the 'users' collection
        db.collection(collectionName).get()
            .addOnSuccessListener { documents ->
                // Iterate over all documents
                for (document in documents) {
                    // Map Firestore document to UserDetailsModel
                    val user =
                        document.toObject(UserDetailsModel::class.java)
                            .copy(documentId = document.id)
                    // Add to list
                    userList.add(user)
                }
                // Return the list via callback
                onResult(userList)
            }
            .addOnFailureListener { e ->
                Log.e(Messages.FIRESTORE_ERROR, Messages.ERROR_FETCHING_USERS, e)
                // Return empty list in case of error
                onResult(null)
            }
    }

    fun addPerson(
        collectionName: String,
        userDetails: UserDetailsModel,
        onResult: (Boolean) -> Unit
    ) {
        // Firestore instance
        val db = FirebaseFirestore.getInstance()
        // Add a new document with auto-generated ID
        db.collection(collectionName)
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

    fun deletePerson(model: UserDetailsModel, collectionName: String, onResult: (Boolean) -> Unit) {
        // Firestore instance
        val db = FirebaseFirestore.getInstance()

        // Check if the model has an ID (or a field that holds the document ID in Firestore)
        val documentId =
            model.documentId // Assuming 'id' is the field holding the Firestore document ID

        if (documentId != null) {
            // Reference the document and delete it
            db.collection(collectionName).document(documentId)
                .delete()
                .addOnSuccessListener {
                    // Deletion was successful, return true
                    onResult(true)
                }
                .addOnFailureListener { e ->
                    // Log the error and return false
                    Log.e(Messages.FIRESTORE_ERROR, Messages.ERROR_DELETING_DOCUMENT, e)
                    onResult(false)
                }
        } else {
            // If no ID is provided, log an error and return false
            Log.e(Messages.FIRESTORE_ERROR, "Document ID is null, cannot delete.")
            onResult(false)
        }
    }
}
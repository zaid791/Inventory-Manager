package com.example.inventorymanager.home.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.inventorymanager.common.FirestoreConstants
import com.example.inventorymanager.common.Messages
import com.example.inventorymanager.home.model.UserDetailsModel
import com.google.firebase.firestore.FirebaseFirestore

class MainViewModel : ViewModel() {

    fun fetchUserDetails(onResult: (List<UserDetailsModel>?) -> Unit) {
        // Firestore instance
        val db = FirebaseFirestore.getInstance()
        // List to hold user details
        val userList = mutableListOf<UserDetailsModel>()
        // Fetch all documents from the 'users' collection
        db.collection(FirestoreConstants.COLLECTION_PEOPLE).get()
            .addOnSuccessListener { documents ->
                // Iterate over all documents
                for (document in documents) {
                    // Map Firestore document to UserDetailsModel
                    val user =
                        document.toObject(UserDetailsModel::class.java)
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
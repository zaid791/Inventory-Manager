package com.example.inventorymanager.home.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.inventorymanager.common.FirestoreConstants
import com.example.inventorymanager.details.model.dataClass.UserDetailsModel
import com.google.firebase.firestore.FirebaseFirestore

class MainViewModel : ViewModel() {
    fun fetchUserDetails(onResult: (List<UserDetailsModel>?) -> Unit) {
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
                    val user =
                        document.toObject(UserDetailsModel::class.java).copy(id = document.id)
                    // Add to list
                    userList.add(user)
                }
                // Return the list via callback
                onResult(userList)
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error fetching users", e)
                // Return empty list in case of error
                onResult(null)
            }
    }

}
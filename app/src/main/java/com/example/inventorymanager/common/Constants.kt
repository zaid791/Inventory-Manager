package com.example.inventorymanager.common

import com.example.inventorymanager.home.model.Items
import com.example.inventorymanager.home.model.TransactionModel
import java.time.LocalDateTime

object Messages {
    fun getFullName(firstName: String, lastName: String): String {
        return firstName + lastName
    }

    fun getAddUserFragmentTitle(collectionName: String): String{
        return "Add $collectionName"
    }

    fun getCurrencyString(amount: Long): String{
        return "₹ $amount"
    }

    fun getEditUserFragmentTitle(collectionName: String): String {
        return "Edit $collectionName"
    }

    const val CANCEL = "Cancel"
    const val PLEASE_TURN_ON_INTERNET = "Please turn on the internet to continue."
    const val TURN_ON_INTERNET = "Turn on Internet"
    const val NO_INTERNET = "No Internet Connection"
    const val BLANK = ""
    const val FIELD_REQUIRED = "This field cannot be empty"
    const val FIRESTORE_ERROR = "FirestoreError"
    const val ERROR_ADDING_DOCUMENT = "Error adding user document"
    const val SUCCESS = "Task Completed Successfully"
    const val INTERNAL_ERROR = "Internal Error"
    const val FIRST_NAME_REQUIRED = "First name is required"
    const val LAST_NAME_REQUIRED = "Last name is required"
    const val COMPANY_NAME_REQUIRED = "Company name is required"
    const val MOBILE_NAME_REQUIRED = "Mobile number is required"
    const val ADDRESS_REQUIRED = "Address is required"
    const val MOBILE_NUMBER_LENGTH = "Mobile number must be 10 digits long"
    const val ERROR_FETCHING_USERS = "Error fetching users"
    const val ERROR_DELETING_DOCUMENT = "Error deleting document"
    const val YES = "Yes"
    const val NO = "No"
    const val ARE_YOU_SURE_DELETE = "Are you want to delete this user ?\nNote:- All the related transaction history will be deleted!"
    // Sample transactions for testing
    val transactionList = listOf(
        TransactionModel(
            transactionId = 1,
            amountPaid = 5000,
            amountPending = 2000,
            dateTime = LocalDateTime.of(2024, 10, 1, 14, 30),
            items = listOf(
                Items(
                    name = "Apples",
                    unitPrice = 100,
                    unitName = UnitEnum.KG,
                    quantity = 50,
                    totalPrice = 5000,
                    amountPaid = 3000,
                    amountPending = 2000
                ),
                Items(
                    name = "Milk",
                    unitPrice = 50,
                    unitName = UnitEnum.LITRE,
                    quantity = 40,
                    totalPrice = 2000,
                    amountPaid = 2000,
                    amountPending = 0
                )
            )
        ),
        TransactionModel(
            transactionId = 2,
            amountPaid = 8000,
            amountPending = 0,
            dateTime = LocalDateTime.of(2024, 10, 2, 10, 15),
            items = listOf(
                Items(
                    name = "Bananas",
                    unitPrice = 150,
                    unitName = UnitEnum.KG,
                    quantity = 40,
                    totalPrice = 6000,
                    amountPaid = 6000,
                    amountPending = 0
                ),
                Items(
                    name = "Bread",
                    unitPrice = 40,
                    unitName = UnitEnum.PIECE,
                    quantity = 50,
                    totalPrice = 2000,
                    amountPaid = 2000,
                    amountPending = 0
                )
            )
        ),
        TransactionModel(
            transactionId = 3,
            amountPaid = 3000,
            amountPending = 5000,
            dateTime = LocalDateTime.of(2024, 10, 3, 9, 0),
            items = listOf(
                Items(
                    name = "Chicken",
                    unitPrice = 400,
                    unitName = UnitEnum.KG,
                    quantity = 20,
                    totalPrice = 8000,
                    amountPaid = 3000,
                    amountPending = 5000
                )
            )
        )
    )
}
object FirestoreConstants{
    const val COLLECTION_SUPPLIER = "Supplier"
    const val COLLECTION_CUSTOMER = "Customer"
}
object PreferencesConstants{
    const val PREF_NAME = "app_preferences"
}
object Patterns{
    const val DATE_TIME = "dd-MM-yyyy HH:mm"
}

enum class SharedPreferenceKey(val key: String) {
    LOG_IN("isLoggedIn"),
    SELECTED_PERSON("selectedPerson"),
}
enum class Actions{
    View,
    Edit,
    Delete
}
enum class UnitEnum {
    KG, LITRE, PIECE
}

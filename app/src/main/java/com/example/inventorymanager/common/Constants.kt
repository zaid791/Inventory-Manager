package com.example.inventorymanager.common

object Messages {
    fun getFullName(firstName: String, lastName: String): String {
        return firstName + lastName
    }

    fun getAddUserFragmentTitle(collectionName: String): String{
        return "Add $collectionName"
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
}
object FirestoreConstants{
    const val COLLECTION_SUPPLIER = "Supplier"
    const val COLLECTION_CUSTOMER = "Customer"
}
object PreferencesConstants{
    const val PREF_NAME = "app_preferences"
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
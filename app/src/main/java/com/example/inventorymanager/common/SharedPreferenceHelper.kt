package com.example.inventorymanager.common

import android.content.Context
import android.content.SharedPreferences
import com.example.inventorymanager.home.model.UserDetailsModel
import com.google.gson.Gson

class SharedPreferenceHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PreferencesConstants.PREF_NAME, Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveLoginStatus(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(SharedPreferenceKey.LOG_IN.key, isLoggedIn).apply()
    }

    fun getLoginStatus(): Boolean {
        return sharedPreferences.getBoolean(SharedPreferenceKey.LOG_IN.key, false)
    }

    //Save UserDetailsModel in preferences
    fun saveSelectedPerson(model: UserDetailsModel) {
        val jsonString = gson.toJson(model)
        sharedPreferences.edit().putString(SharedPreferenceKey.SELECTED_PERSON.key, jsonString)
            .apply()
    }

    // Retrieve the UserDetailsModel object from SharedPreferences
    fun getSelectedPerson(): UserDetailsModel? {
        val jsonString = sharedPreferences.getString(SharedPreferenceKey.SELECTED_PERSON.key, null)
        return if (jsonString != null) {
            gson.fromJson(jsonString, UserDetailsModel::class.java)
        } else {
            null
        }
    }
}
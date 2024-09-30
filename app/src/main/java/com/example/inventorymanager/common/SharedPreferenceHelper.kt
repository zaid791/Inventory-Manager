package com.example.inventorymanager.common

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PreferencesConstants.PREF_NAME, Context.MODE_PRIVATE)

    fun saveLoginStatus(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(SharedPreferenceKey.LOG_IN.key, isLoggedIn).apply()
    }

    fun getLoginStatus(): Boolean {
        return sharedPreferences.getBoolean(SharedPreferenceKey.LOG_IN.key, false)
    }
}
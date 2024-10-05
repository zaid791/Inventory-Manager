package com.example.inventorymanager.common

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


class CommonViewModel : ViewModel() {
    fun startLoading(mainProgressBar: View, mainLayout: View) {
        mainProgressBar.isVisible = true
        mainLayout.isVisible = false
    }

    fun stopLoading(mainProgressBar: View, mainLayout: View) {
        mainProgressBar.isVisible = false
        mainLayout.isVisible = true
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun showNoInternetDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(Messages.NO_INTERNET)
        builder.setMessage(Messages.PLEASE_TURN_ON_INTERNET)
        // Positive button to redirect to network settings
        builder.setPositiveButton(Messages.TURN_ON_INTERNET) { dialog: DialogInterface, _: Int ->
            // Open the Wi-Fi or Network settings page
            val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            context.startActivity(intent)
            dialog.dismiss()
        }

        // Optionally, add a Cancel button
        builder.setNegativeButton(Messages.CANCEL) { dialog: DialogInterface, _: Int ->
            dialog.dismiss() // Close the dialog if user cancels
        }
        builder.setCancelable(false) // Prevents the user from dismissing the dialog by clicking outside
        builder.show()
    }


    fun vibrateDevice(context: Context, duration: Long = 500) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Use VibratorManager for Android 12 (API 31) and above
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrator = vibratorManager.defaultVibrator

            // Create and execute a one-shot vibration
            val vibrationEffect = VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Use the old Vibrator class for Android O to Android 11 (API 26 to 30)
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val vibrationEffect = VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        }
    }



    // Function to convert LocalDateTime to a readable string
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateTimeToReadableString(dateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern(Patterns.DATE_TIME)  // Define the desired format
        return dateTime.format(formatter)  // Format and return the result as a string
    }

    // Function to convert a readable string to LocalDateTime
    @RequiresApi(Build.VERSION_CODES.O)
    fun parseReadableStringToDateTime(dateTimeString: String): LocalDateTime? {
        return try {
            val formatter = DateTimeFormatter.ofPattern(Patterns.DATE_TIME)  // Define the same format
            LocalDateTime.parse(dateTimeString, formatter)  // Parse and return the LocalDateTime object
        } catch (e: DateTimeParseException) {
            e.printStackTrace()
            null  // Return null if parsing fails (optional)
        }
    }

}
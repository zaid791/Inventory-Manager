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


}
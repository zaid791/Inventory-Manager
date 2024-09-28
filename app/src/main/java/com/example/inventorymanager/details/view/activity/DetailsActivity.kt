package com.example.inventorymanager.details.view.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.inventorymanager.R
import com.example.inventorymanager.common.CommonViewModel
import com.example.inventorymanager.common.NetworkConnectionCallback

class DetailsActivity : AppCompatActivity() {
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkConnectionCallback: NetworkConnectionCallback
    private val commonViewModel = CommonViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }
        // Check for internet connection when the app opens
        if (!NetworkConnectionCallback.isInternetAvailable(this)) {
            commonViewModel.showNoInternetDialog(this)
        }

        // Setup Connectivity Manager and Network Callback
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkConnectionCallback = NetworkConnectionCallback { isConnected ->
            if (!isConnected) {
                commonViewModel.showNoInternetDialog(this)
            }
        }
    }
}
package com.example.inventorymanager.home.view.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.inventorymanager.R
import com.example.inventorymanager.common.CommonViewModel
import com.example.inventorymanager.common.NetworkConnectionCallback
import com.example.inventorymanager.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkConnectionCallback: NetworkConnectionCallback
    private val commonViewModel = CommonViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.main.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
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

        registerNetworkCallback()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)

        // Hook up BottomNavigationView with NavController
        bottomNavView.setupWithNavController(navController)
    }

    private fun registerNetworkCallback() {
        connectivityManager.registerDefaultNetworkCallback(networkConnectionCallback)
    }
}
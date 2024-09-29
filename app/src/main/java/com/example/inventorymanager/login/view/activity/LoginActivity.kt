package com.example.inventorymanager.login.view.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.inventorymanager.common.CommonViewModel
import com.example.inventorymanager.common.Messages
import com.example.inventorymanager.common.NetworkConnectionCallback
import com.example.inventorymanager.databinding.ActivityLoginBinding
import com.example.inventorymanager.home.view.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkConnectionCallback: NetworkConnectionCallback
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val commonViewModel = CommonViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.main.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
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
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener {
            loginUser(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString(),
            )

        }
    }

    private fun loginUser(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign-in successful, update UI with the signed-in user's information
                        val user = auth.currentUser
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        // Navigate to home screen
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        // If sign-in fails, display a message to the user
                        Toast.makeText(
                            this,
                            "Authentication Failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            if (email.isEmpty()) binding.emailEditText.error = Messages.FIELD_REQUIRED
            if (password.isEmpty()) binding.passwordEditText.error = Messages.FIELD_REQUIRED
        }
    }
}
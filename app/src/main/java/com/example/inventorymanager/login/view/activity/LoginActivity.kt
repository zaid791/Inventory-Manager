package com.example.inventorymanager.login.view.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.inventorymanager.common.CommonViewModel
import com.example.inventorymanager.common.Messages
import com.example.inventorymanager.common.NetworkConnectionCallback
import com.example.inventorymanager.common.SharedPreferenceHelper
import com.example.inventorymanager.databinding.ActivityLoginBinding
import com.example.inventorymanager.home.view.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkConnectionCallback: NetworkConnectionCallback
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private val commonViewModel = CommonViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        sharedPreferenceHelper = SharedPreferenceHelper(this)
        setupViewAndInternetChecker(view)
        // Check if the user is already logged in
        if (sharedPreferenceHelper.getLoginStatus()) {
            // Initialize biometric authentication
            setupBiometricAuthentication()
            authenticateUser()
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

    private fun setupBiometricAuthentication() {
        val biometricManager = BiometricManager.from(this)

        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                // Biometric authentication is available
                setupBiometricPrompt()
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                // No biometric features available on the device
                Toast.makeText(this, "No biometric hardware available", Toast.LENGTH_SHORT).show()
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                // Biometric features are currently unavailable
                Toast.makeText(
                    this,
                    "Biometric features are currently unavailable",
                    Toast.LENGTH_SHORT
                ).show()
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // The user hasn't enrolled any biometric credentials
                Toast.makeText(this, "No biometric credentials enrolled", Toast.LENGTH_SHORT).show()
            }

            else -> {
                Toast.makeText(this, "Biometric status unknown or unsupported", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setupBiometricPrompt() {
        val executor: Executor = ContextCompat.getMainExecutor(this)
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    // Authentication succeeded
                    sharedPreferenceHelper.saveLoginStatus(true)
                    Toast.makeText(
                        this@LoginActivity,
                        "Authentication Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Authentication error: $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationFailed() {
                    Toast.makeText(this@LoginActivity, "Authentication failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Use your biometric credential to log in")
            .setNegativeButtonText("Use PIN")
            .build()
    }

    private fun authenticateUser() {
        biometricPrompt.authenticate(promptInfo)
    }

//    private fun showPinDialog() {
//        val dialog = PinInputDialog() // Create a custom dialog to enter PIN
//        dialog.setOnPinEnteredListener { pin ->
//            // Verify the PIN (you can store the correct PIN securely, e.g., in SharedPreferences)
//            if (pin == "1234") { // Example: replace with your logic
//                sharedPreferenceHelper.saveLoginStatus(true)
//                startActivity(Intent(this, MainActivity::class.java))
//            } else {
//                Toast.makeText(this, "Invalid PIN", Toast.LENGTH_SHORT).show()
//            }
//        }
//        dialog.show(supportFragmentManager, "PinInputDialog")
//    }

    private fun setupViewAndInternetChecker(view: LinearLayout) {
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
    }

    private fun loginUser(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign-in successful, update UI with the signed-in user's information
//                        val user = auth.currentUser
                        sharedPreferenceHelper.saveLoginStatus(true)
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
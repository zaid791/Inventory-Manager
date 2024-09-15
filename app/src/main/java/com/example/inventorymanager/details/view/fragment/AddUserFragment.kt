package com.example.inventorymanager.details.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.inventorymanager.common.CommonViewModel
import com.example.inventorymanager.common.Messages
import com.example.inventorymanager.databinding.FragmentAddUserBinding
import com.example.inventorymanager.details.model.dataClass.UserDetailsModel
import com.example.inventorymanager.details.viewModel.DetailsViewModel
import com.example.inventorymanager.home.view.activity.MainActivity
import com.google.android.material.textfield.TextInputEditText

class AddUserFragment : Fragment() {
    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!
    private val commonViewModel = CommonViewModel()
    private lateinit var detailsViewModel: DetailsViewModel
    override fun onAttach(context: Context) {
        super.onAttach(context)
        detailsViewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        val view = binding.root

        // Detect if the device has a cutout/notch
        view.setOnApplyWindowInsetsListener { _, insets ->
            val displayCutout = insets.displayCutout
            if (displayCutout != null) {
                // Increase the top margin by the height of the cutout
                val cutoutHeight = displayCutout.safeInsetTop
                val layoutParams = binding.topAppbar.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.topMargin = cutoutHeight
                binding.topAppbar.layoutParams = layoutParams
            }
            insets
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancel.setOnClickListener {
            startMainActivity()
        }

        binding.btnAdd.setOnClickListener {
            commonViewModel.startLoading(binding.mainProgressBar, binding.mainLayout)
            // Call the function to validate and create the user object
            validateAndCreateUser()
        }
    }

    private fun validateAndCreateUser() {
        // Validate required fields dynamically
        val firstName = validateField(binding.etFirstName, Messages.FIRST_NAME_REQUIRED)
        val lastName = validateField(binding.etLastName, Messages.LAST_NAME_REQUIRED)
        val companyName = validateField(binding.etCompanyName, Messages.COMPANY_NAME_REQUIRED)
        val mobileNumberText = validateField(binding.etMobileNumber, Messages.MOBILE_NAME_REQUIRED)
        val address = validateField(binding.etAddress, Messages.ADDRESS_REQUIRED)
        val alias = binding.etAlias.text.toString().ifEmpty { null }

        // Special validation for mobile number - must be 10 digits long and numeric
        val mobileNumber =
            if (mobileNumberText != null && mobileNumberText.matches("\\d{10}".toRegex())) {
                mobileNumberText.toLongOrNull()
            } else {
                commonViewModel.stopLoading(binding.mainProgressBar, binding.mainLayout)
                binding.etMobileNumber.error = Messages.MOBILE_NUMBER_LENGTH
                null
            }

        // If all fields are valid, create UserDetailsModel
        if (firstName != null && lastName != null && companyName != null && mobileNumber != null && address != null) {
            val userDetails = UserDetailsModel(
                firstName = firstName,
                lastName = lastName,
                companyName = companyName,
                alias = alias,
                mobileNumber = mobileNumber.toInt(), // Assuming mobile number fits into Int range
                address = address
            )
            detailsViewModel.addPerson(userDetails) { isSuccess ->
                if (isSuccess) {
                    startMainActivity()
                } else {
                    commonViewModel.stopLoading(binding.mainProgressBar, binding.mainLayout)
                    Toast.makeText(requireContext(), Messages.INTERNAL_ERROR, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            // You can now use userDetails, e.g., save it to the database or pass it to another function
        }
    }

    private fun startMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    // Helper function to validate a field
    private fun validateField(editText: TextInputEditText, errorMessage: String): String? {
        return if (editText.text.isNullOrEmpty()) {
            editText.error = errorMessage
            commonViewModel.stopLoading(binding.mainProgressBar, binding.mainLayout)
            null
        } else {
            editText.text.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
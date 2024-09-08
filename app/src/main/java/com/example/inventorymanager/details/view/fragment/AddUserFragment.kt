package com.example.inventorymanager.details.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.inventorymanager.common.CommonViewModel
import com.example.inventorymanager.databinding.FragmentAddUserBinding
import com.example.inventorymanager.details.model.dataClass.UserDetailsModel
import com.google.android.material.textfield.TextInputEditText

class AddUserFragment : Fragment() {
    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!
    private val commonViewModel = CommonViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancel.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnAdd.setOnClickListener {
            commonViewModel.startLoading(binding.mainProgressBar, binding.mainLayout)
            // Call the function to validate and create the user object
            validateAndCreateUser()
        }
    }

    private fun validateAndCreateUser() {
        // Validate required fields dynamically
        val firstName = validateField(binding.etFirstName, "First name is required")
        val lastName = validateField(binding.etLastName, "Last name is required")
        val companyName = validateField(binding.etCompanyName, "Company name is required")
        val mobileNumberText = validateField(binding.etMobileNumber, "Mobile number is required")
        val address = validateField(binding.etAddress, "Address is required")
        val alias = binding.etAlias.text.toString().ifEmpty { null }

        // Special validation for mobile number - must be 10 digits long and numeric
        val mobileNumber =
            if (mobileNumberText != null && mobileNumberText.matches("\\d{10}".toRegex())) {
                mobileNumberText.toLongOrNull()
            } else {
                binding.etMobileNumber.error = "Mobile number must be 10 digits long"
                null
            }

        // If all fields are valid, create UserDetailsModel
        if (firstName != null && lastName != null && companyName != null && mobileNumber != null && address != null) {
            val userDetails = UserDetailsModel(
                id = null, // Assuming this is auto-generated
                firstName = firstName,
                lastName = lastName,
                companyName = companyName,
                alias = alias,
                mobileNumber = mobileNumber.toInt(), // Assuming mobile number fits into Int range
                address = address
            )

            // You can now use userDetails, e.g., save it to the database or pass it to another function
        }
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

}
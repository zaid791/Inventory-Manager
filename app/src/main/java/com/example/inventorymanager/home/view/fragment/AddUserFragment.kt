package com.example.inventorymanager.home.view.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventorymanager.R
import com.example.inventorymanager.common.CommonViewModel
import com.example.inventorymanager.common.Messages
import com.example.inventorymanager.common.NavigationHelper
import com.example.inventorymanager.databinding.FragmentAddUserBinding
import com.example.inventorymanager.home.model.UserDetailsModel
import com.example.inventorymanager.home.viewModel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class AddUserFragment : Fragment() {
    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!
    private val commonViewModel = CommonViewModel()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var navigationHelper: NavigationHelper
    private val args: AddUserFragmentArgs by navArgs()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        navigationHelper = NavigationHelper(findNavController())
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        binding.tvTitle.text = Messages.getAddUserFragmentTitle(args.collectionName)
        hideActivityElements()
        return binding.root
    }

    private fun hideActivityElements() {
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        fab.isVisible = false
        val bottomNavBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNavBar.isVisible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancel.setOnClickListener {
            navigationHelper.navigateBackward()
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
                commonViewModel.vibrateDevice(requireContext())
                binding.etMobileNumber.error = Messages.MOBILE_NUMBER_LENGTH
                null
            }

        // If all fields are valid, create UserDetailsModel
        if (firstName != null && lastName != null && companyName != null && mobileNumber != null && address != null) {
            val userDetails = UserDetailsModel(
                id = setupId(firstName, lastName, alias, mobileNumber),
                firstName = firstName,
                lastName = lastName,
                companyName = companyName,
                alias = alias,
                mobileNumber = mobileNumber.toInt(), // Assuming mobile number fits into Int range
                address = address,
                transactions = listOf()
            )
            mainViewModel.addPerson(args.collectionName, userDetails) { isSuccess ->
                if (isSuccess) {
                    navigationHelper.navigateBackward()
                } else {
                    commonViewModel.stopLoading(binding.mainProgressBar, binding.mainLayout)
                    Toast.makeText(requireContext(), Messages.INTERNAL_ERROR, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setupId(
        firstName: String,
        lastName: String,
        alias: String?,
        mobileNumber: Long
    ): String {
        // If alias is null, use first and last name for the ID
        val idAlias = alias ?: "$firstName.$lastName"

        // Format the mobile number (convert to string)
        val formattedMobile = mobileNumber.toString()

        // Combine all elements to form the ID
        return "$idAlias-$formattedMobile"
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
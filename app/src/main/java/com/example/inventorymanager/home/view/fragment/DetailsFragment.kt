package com.example.inventorymanager.home.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventorymanager.R
import com.example.inventorymanager.common.CommonViewModel
import com.example.inventorymanager.common.Messages
import com.example.inventorymanager.common.SharedPreferenceHelper
import com.example.inventorymanager.databinding.FragmentDetailsBinding
import com.example.inventorymanager.home.model.UserDetailsModel
import com.example.inventorymanager.home.viewModel.adapter.TransactionHistoryAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private lateinit var userDetailsModel: UserDetailsModel
    private val commonViewModel = CommonViewModel()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferenceHelper = SharedPreferenceHelper(context)
        userDetailsModel = sharedPreferenceHelper.getSelectedPerson()!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        setupActivityUi()
        setupFragmentUi()
        return binding.root
    }

    private fun setupFragmentUi() {
        with(binding) {
            tvName.text = userDetailsModel.alias ?: userDetailsModel.firstName
            tvFullName.text =
                Messages.getFullName(userDetailsModel.firstName, userDetailsModel.lastName)
            tvAddress.text = userDetailsModel.address
            tvCompanyName.text = userDetailsModel.companyName
            userDetailsModel.mobileNumber.toString().also { tvMobileNumber.text = it }
            tvTotalAmountPaidAllTransactions.text =
                Messages.getCurrencyString(userDetailsModel.totalAmountPaidAllTransactions)
            tvTotalAmountPendingAllTransactions.text =
                Messages.getCurrencyString(userDetailsModel.totalAmountPendingAllTransactions)
            rvSupplierAmountHistory.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = TransactionHistoryAdapter(
                    transactions = userDetailsModel.transactions,
                    commonViewModel = commonViewModel
                )
            }
        }
    }

    private fun setupActivityUi() {
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        fab.isVisible = false
        val bottomNavBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNavBar.isVisible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnExpand.setOnClickListener {
                showDetails(true)
            }
            btnCollapse.setOnClickListener {
                showDetails(false)
            }
            btnAddTransaction.setOnClickListener {
                findNavController().navigate(
                    DetailsFragmentDirections.actionDetailsFragmentToNewProductPurchaseFromSupplierFragment()
                )
            }
        }
    }

    private fun showDetails(showDetails: Boolean) {
        with(binding) {
            btnExpand.isVisible = !showDetails
            btnCollapse.isVisible = showDetails
            llMoreInfo.isVisible = showDetails
        }
    }
}
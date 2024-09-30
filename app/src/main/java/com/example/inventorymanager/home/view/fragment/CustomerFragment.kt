package com.example.inventorymanager.home.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventorymanager.R
import com.example.inventorymanager.common.Actions
import com.example.inventorymanager.common.CommonViewModel
import com.example.inventorymanager.common.FirestoreConstants
import com.example.inventorymanager.common.Messages
import com.example.inventorymanager.databinding.FragmentCustomerBinding
import com.example.inventorymanager.home.model.UserDetailsModel
import com.example.inventorymanager.home.viewModel.MainViewModel
import com.example.inventorymanager.home.viewModel.adapter.BuyerSellerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CustomerFragment : Fragment() {
    private var _binding: FragmentCustomerBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private val commonViewModel = CommonViewModel()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerBinding.inflate(inflater, container, false)
        commonViewModel.startLoading(binding.mainProgressBar, binding.rvCustomer)
        getData()
        return binding.root
    }

    private fun getData() {
        mainViewModel.fetchUserDetails(FirestoreConstants.COLLECTION_CUSTOMER) { response ->
            if (!response.isNullOrEmpty()) {
                setupAdapter(response)
            } else if (response == null) {
                commonViewModel.stopLoading(binding.mainProgressBar, binding.rvCustomer)
                Toast.makeText(requireContext(), Messages.INTERNAL_ERROR, Toast.LENGTH_SHORT).show()
            } else {
                commonViewModel.stopLoading(binding.mainProgressBar, binding.emptyPlaceholder)
            }
        }
    }

    private fun setupAdapter(response: List<UserDetailsModel>) {
        binding.rvCustomer.apply {
            adapter = BuyerSellerAdapter(response) { model, action ->
                when (action) {
                    Actions.View -> TODO()
                    Actions.Edit -> TODO()
                    Actions.Delete -> mainViewModel.deletePerson(model)
                }
            }
            layoutManager = LinearLayoutManager(requireContext())
        }
        commonViewModel.stopLoading(binding.mainProgressBar, binding.rvCustomer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        fab.isVisible = true
        val bottomNavBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNavBar.isVisible = true
        fab.setOnClickListener {
            val action = CustomerFragmentDirections.actionCustomerFragmentToAddUserFragment(
                FirestoreConstants.COLLECTION_CUSTOMER
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
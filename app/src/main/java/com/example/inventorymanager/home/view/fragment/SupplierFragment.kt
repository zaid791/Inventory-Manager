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
import com.example.inventorymanager.common.CommonViewModel
import com.example.inventorymanager.common.Messages
import com.example.inventorymanager.databinding.FragmentSupplierBinding
import com.example.inventorymanager.details.model.dataClass.UserDetailsModel
import com.example.inventorymanager.home.model.response.NavigationHelper
import com.example.inventorymanager.home.viewModel.MainViewModel
import com.example.inventorymanager.home.viewModel.adapter.BuyerSellerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SupplierFragment : Fragment() {
    private var _binding: FragmentSupplierBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private val commonViewModel = CommonViewModel()
    private lateinit var navigationHelper: NavigationHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationHelper = NavigationHelper(findNavController())
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSupplierBinding.inflate(inflater, container, false)
        commonViewModel.startLoading(binding.mainProgressBar, binding.rvBuyers)
        getData()
        val view = binding.root
        return view
    }

    private fun getData() {
        mainViewModel.fetchUserDetails { response ->
            if (!response.isNullOrEmpty()) {
                setupAdapter(response)
            } else if (response == null) {
                commonViewModel.stopLoading(binding.mainProgressBar, binding.rvBuyers)
                Toast.makeText(requireContext(), Messages.INTERNAL_ERROR, Toast.LENGTH_SHORT)
                    .show()
            } else {
                commonViewModel.stopLoading(binding.mainProgressBar, binding.emptyPlaceholder)
            }
        }
    }

    private fun setupAdapter(response: List<UserDetailsModel>) {
        binding.rvBuyers.apply {
            adapter = BuyerSellerAdapter(response)
            layoutManager = LinearLayoutManager(requireContext())
        }
        commonViewModel.stopLoading(binding.mainProgressBar, binding.rvBuyers)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        fab.isVisible = true
        val bottomNavBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNavBar.isVisible = true
        fab.setOnClickListener {
            navigationHelper.navigateWithAnimation(R.id.action_supplierFragment_to_addUserFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
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
import com.example.inventorymanager.common.NavigationHelper
import com.example.inventorymanager.databinding.FragmentSupplierBinding
import com.example.inventorymanager.home.model.UserDetailsModel
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
        commonViewModel.startLoading(binding.mainProgressBar, binding.rvSupplier)
        getData()
        return binding.root
    }

    private fun getData() {
        mainViewModel.fetchUserDetails(FirestoreConstants.COLLECTION_SUPPLIER) { response ->
            if (!response.isNullOrEmpty()) {
                setupAdapter(response)
            } else if (response == null) {
                commonViewModel.stopLoading(binding.mainProgressBar, binding.rvSupplier)
                Toast.makeText(requireContext(), Messages.INTERNAL_ERROR, Toast.LENGTH_SHORT)
                    .show()
            } else {
                commonViewModel.stopLoading(binding.mainProgressBar, binding.emptyPlaceholder)
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupAdapter(response: List<UserDetailsModel>) {
        binding.rvSupplier.apply {
            adapter = BuyerSellerAdapter(response) { model, action ->
                when (action) {
                    Actions.View -> TODO()
                    Actions.Edit -> TODO()
                    Actions.Delete -> {
                        commonViewModel.startLoading(binding.mainProgressBar, binding.rvSupplier)
                        mainViewModel.deletePerson(
                            model,
                            FirestoreConstants.COLLECTION_SUPPLIER
                        ) { isSuccess ->
                            commonViewModel.stopLoading(binding.mainProgressBar, binding.rvSupplier)
                            if (isSuccess){
                                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
            layoutManager = LinearLayoutManager(requireContext())
        }
        commonViewModel.stopLoading(binding.mainProgressBar, binding.rvSupplier)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        fab.isVisible = true
        val bottomNavBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNavBar.isVisible = true
        fab.setOnClickListener {
            val action = SupplierFragmentDirections.actionSupplierFragmentToAddUserFragment(
                FirestoreConstants.COLLECTION_SUPPLIER
            )
            findNavController().navigate(action)
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            getData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.inventorymanager.home.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.example.inventorymanager.common.SharedPreferenceHelper
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
    private var personList: MutableList<UserDetailsModel> = mutableListOf()
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        sharedPreferenceHelper = SharedPreferenceHelper(context)
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
            handleDataResponse(response)
        }
    }

    private fun handleDataResponse(response: List<UserDetailsModel>?) {
        if (!response.isNullOrEmpty()) {
            personList = response.toMutableList()
            setupAdapter()
        } else if (response == null) {
            commonViewModel.stopLoading(binding.mainProgressBar, binding.rvSupplier)
            Toast.makeText(requireContext(), Messages.INTERNAL_ERROR, Toast.LENGTH_SHORT)
                .show()
        } else {
            commonViewModel.stopLoading(binding.mainProgressBar, binding.emptyPlaceholder)
        }
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun setupAdapter() {
        binding.rvSupplier.apply {
            adapter = BuyerSellerAdapter(personList) { model, action ->
                when (action) {
                    Actions.View -> viewPersonDetails(model)
                    Actions.Edit -> editPersonalDetails(model)
                    Actions.Delete -> showDialog(model)
                }
            }
            layoutManager = LinearLayoutManager(requireContext())
        }
        commonViewModel.stopLoading(binding.mainProgressBar, binding.rvSupplier)
    }

    private fun editPersonalDetails(model: UserDetailsModel) {
        sharedPreferenceHelper.saveSelectedPerson(model)
        val action = SupplierFragmentDirections.actionSupplierFragmentToAddUserFragment(
            collectionName = FirestoreConstants.COLLECTION_SUPPLIER,
            isEdit = true
        )
        findNavController().navigate(action)
    }

    private fun viewPersonDetails(model: UserDetailsModel) {
        sharedPreferenceHelper.saveSelectedPerson(model)
        val action = SupplierFragmentDirections.actionSupplierFragmentToDetailsFragment()
        findNavController().navigate(action)
    }

    // Function to actually show the dialog
    private fun showDialog(model: UserDetailsModel) {
        AlertDialog.Builder(requireContext())
            .setTitle(Actions.Delete.name)
            .setMessage(Messages.ARE_YOU_SURE_DELETE)
            .setPositiveButton(Messages.YES) { dialog, _ ->
                deletePerson(model)
                dialog.dismiss()
            }
            .setNegativeButton(Messages.NO) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun deletePerson(model: UserDetailsModel) {
        commonViewModel.startLoading(binding.mainProgressBar, binding.rvSupplier)
        mainViewModel.deletePerson(
            model,
            FirestoreConstants.COLLECTION_SUPPLIER
        ) { isSuccess ->
            if (isSuccess) {
                personList.remove(model)
                handleDataResponse(personList)
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT)
                    .show()
            } else {
                commonViewModel.stopLoading(binding.mainProgressBar, binding.rvSupplier)
                Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show()
            }
        }
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
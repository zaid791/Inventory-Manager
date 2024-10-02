package com.example.inventorymanager.home.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventorymanager.R
import com.example.inventorymanager.databinding.FragmentDetailsBinding
import com.example.inventorymanager.home.model.SupplierAmount
import com.example.inventorymanager.home.viewModel.adapter.SupplierAmountHistoryAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsFragment : Fragment() {
    lateinit var binding: FragmentDetailsBinding
    private lateinit var SupplierAmountHistoryAdapter: SupplierAmountHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        fab.isVisible = false
        val bottomNavBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNavBar.isVisible = false
        val dataList = listOf(
            SupplierAmount("01-08-2024", "1000 ₹"),
            SupplierAmount("02-08-2024", "1500 ₹"),
            SupplierAmount("03-08-2024", "2500 ₹"),
            SupplierAmount("04-08-2024", "3500 ₹"),
            SupplierAmount("05-08-2024", "4500 ₹"),
            SupplierAmount("06-08-2024", "6500 ₹"),
        )

        binding.rvSupplierAmountHistory.layoutManager = LinearLayoutManager(context)

        binding.rvSupplierAmountHistory.adapter = SupplierAmountHistoryAdapter(dataList)

        binding.addNewPurchase.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToNewProductPurchaseFromSupplierFragment())
        }

        return binding.root
    }
}
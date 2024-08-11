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
import com.example.inventorymanager.databinding.FragmentSupplierDetailsBinding
import com.example.inventorymanager.home.DataClass.supplierAmount
import com.example.inventorymanager.home.viewModel.adapter.SupplierAmountHistoryAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SupplierDetailsFragment : Fragment() {
    lateinit var binding: FragmentSupplierDetailsBinding
    private lateinit var SupplierAmountHistoryAdapter: SupplierAmountHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSupplierDetailsBinding.inflate(layoutInflater)
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        fab.isVisible = false
        val bottomNavBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNavBar.isVisible = false
        val dataList = listOf(
            supplierAmount("01-08-2024", "1000 ₹"),
            supplierAmount("02-08-2024", "1500 ₹"),
            supplierAmount("03-08-2024", "2500 ₹"),
            supplierAmount("04-08-2024", "3500 ₹"),
            supplierAmount("05-08-2024", "4500 ₹"),
            supplierAmount("06-08-2024", "6500 ₹"),
        )

        binding.rvSupplierAmountHistory.layoutManager = LinearLayoutManager(context)

        binding.rvSupplierAmountHistory.adapter = SupplierAmountHistoryAdapter(dataList)

        binding.addNewPurchase.setOnClickListener {
            findNavController().navigate(SupplierDetailsFragmentDirections.actionSupplierDetailsFragmentToNewProductPurchaseFromSupplierFragment())
        }

        return binding.root
    }
}
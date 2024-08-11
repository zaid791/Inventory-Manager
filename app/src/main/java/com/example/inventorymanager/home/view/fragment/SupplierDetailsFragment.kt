package com.example.inventorymanager.home.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inventorymanager.R
import com.example.inventorymanager.databinding.FragmentInventoryBinding
import com.example.inventorymanager.databinding.FragmentSupplierBinding
import com.example.inventorymanager.databinding.FragmentSupplierDetailsBinding
import com.example.inventorymanager.home.DataClass.supplierAmount
import com.example.inventorymanager.home.viewModel.adapter.SupplierAmountHistoryAdapter

class SupplierDetailsFragment : Fragment() {
    lateinit var binding : FragmentSupplierDetailsBinding
    private lateinit var SupplierAmountHistoryAdapter: SupplierAmountHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSupplierDetailsBinding.inflate(layoutInflater)

        val dataList = listOf(
            supplierAmount("01-08-2024", "1000 ₹"),
            supplierAmount("02-08-2024", "1500 ₹"),
            supplierAmount("03-08-2024", "2500 ₹"),
            supplierAmount("04-08-2024", "3500 ₹"),
            supplierAmount("05-08-2024", "4500 ₹"),
            supplierAmount("06-08-2024", "6500 ₹"),
        )

        SupplierAmountHistoryAdapter = SupplierAmountHistoryAdapter(dataList)
        binding.rvSupplierAmountHistory.adapter = SupplierAmountHistoryAdapter

        return binding.root
    }
}
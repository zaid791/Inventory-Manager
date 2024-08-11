package com.example.inventorymanager.home.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inventorymanager.R
import com.example.inventorymanager.databinding.FragmentCustomerBinding
import com.example.inventorymanager.databinding.FragmentInventoryBinding
import com.example.inventorymanager.home.DataClass.supplierAmount
import com.example.inventorymanager.home.viewModel.adapter.SupplierAmountHistoryAdapter

class InventoryFragment : Fragment() {
    lateinit var binding : FragmentInventoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInventoryBinding.inflate(layoutInflater)
        return binding.root
    }


}
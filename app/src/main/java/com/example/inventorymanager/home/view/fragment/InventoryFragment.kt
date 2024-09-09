package com.example.inventorymanager.home.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventorymanager.R
import com.example.inventorymanager.databinding.FragmentCustomerBinding
import com.example.inventorymanager.databinding.FragmentInventoryBinding
import com.example.inventorymanager.home.DataClass.InventoryItem
import com.example.inventorymanager.home.DataClass.supplierAmount
import com.example.inventorymanager.home.viewModel.MainViewModel
import com.example.inventorymanager.home.viewModel.adapter.BuyerSellerAdapter
import com.example.inventorymanager.home.viewModel.adapter.InventoryAdapter
import com.example.inventorymanager.home.viewModel.adapter.SupplierAmountHistoryAdapter

class InventoryFragment : Fragment() {
    lateinit var binding : FragmentInventoryBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: InventoryAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInventoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = mainViewModel.list

        val inventoryList = listOf(
            InventoryItem("ARAMI ATTAR", "12 Units", "Attar", "999$", R.drawable.img),
            InventoryItem("Another Item", "5 Units", "Perfume", "500$", R.drawable.img_1),
            InventoryItem("Another Item", "5 Units", "Perfume", "500$", R.drawable.img_3),
            InventoryItem("Another Item", "5 Units", "Perfume", "500$", R.drawable.img_4),
            InventoryItem("Another Item", "5 Units", "Perfume", "500$", R.drawable.img_1),
            InventoryItem("Another Item", "5 Units", "Perfume", "500$", R.drawable.img_1),
            // Add more items here
        )

        adapter = InventoryAdapter(inventoryList)
        binding.rvInventory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@InventoryFragment.adapter
        }
    }


}
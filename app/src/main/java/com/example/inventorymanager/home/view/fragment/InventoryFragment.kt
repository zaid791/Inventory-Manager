package com.example.inventorymanager.home.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventorymanager.R
import com.example.inventorymanager.databinding.FragmentInventoryBinding
import com.example.inventorymanager.details.view.activity.DetailsActivity
import com.example.inventorymanager.details.view.fragment.AddProductFragment
import com.example.inventorymanager.home.DataClass.InventoryItem
import com.example.inventorymanager.home.model.response.NavigationHelper
import com.example.inventorymanager.home.viewModel.MainViewModel
import com.example.inventorymanager.home.viewModel.adapter.InventoryAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class InventoryFragment : Fragment() {
    lateinit var binding : FragmentInventoryBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: InventoryAdapter
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
        binding = FragmentInventoryBinding.inflate(layoutInflater)

        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        fab.isVisible = true
        val bottomNavBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNavBar.isVisible = true
        fab.setOnClickListener {
            navigationHelper.navigateWithAnimation(R.id.action_inventoryFragment_to_addProductFragment2)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
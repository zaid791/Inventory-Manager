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
import com.example.inventorymanager.databinding.FragmentSupplierBinding
import com.example.inventorymanager.details.view.activity.DetailsActivity
import com.example.inventorymanager.home.viewModel.MainViewModel
import com.example.inventorymanager.home.viewModel.adapter.BuyerSellerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SupplierFragment : Fragment() {
    private var _binding: FragmentSupplierBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSupplierBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = mainViewModel.list
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        fab.isVisible = true
        val bottomNavBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNavBar.isVisible = true
        fab.setOnClickListener {
            val intent = Intent(requireContext(), DetailsActivity::class.java)
            startActivity(intent)
        }

        binding.rvBuyers.apply {
            adapter = BuyerSellerAdapter(list) {
                findNavController().navigate(SupplierFragmentDirections.actionSupplierFragmentToSupplierDetailsFragment())
            }
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
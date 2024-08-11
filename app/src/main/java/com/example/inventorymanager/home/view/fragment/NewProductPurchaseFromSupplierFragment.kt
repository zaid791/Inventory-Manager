package com.example.inventorymanager.home.view.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventorymanager.R
import com.example.inventorymanager.databinding.FragmentNewProductPurchaseFromSupplierBinding
import com.example.inventorymanager.home.DataClass.NewProductSupplierDataClass
import com.example.inventorymanager.home.viewModel.adapter.AddNewPurchaseSupplierAdapter
import java.util.Calendar

class NewProductPurchaseFromSupplierFragment : Fragment() {

    lateinit var binding :FragmentNewProductPurchaseFromSupplierBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewProductPurchaseFromSupplierBinding.inflate(layoutInflater)

        binding.editTextDate.setOnClickListener {
            showDatePickerDialog()
        }


        return binding.root
    }

    private fun showDatePickerDialog() {
        // Get the current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create and show the DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format the selected date and update the EditText
                val formattedDate = String.format("%02d-%02d-%d", selectedDay, selectedMonth + 1, selectedYear)
                binding.editTextDate.setText(formattedDate)
            },
            year, month, day
        )
        datePickerDialog.show()



        // Create sample data
        val items = listOf(
            NewProductSupplierDataClass("Item 1", "kg", "100", "5", "10%"),
            NewProductSupplierDataClass("Item 2", "ml", "200", "10", "5%")
        )

        binding.rvNewItemsFromSupplier.layoutManager = LinearLayoutManager(context)

        binding.rvNewItemsFromSupplier.adapter = AddNewPurchaseSupplierAdapter(items)
    }


}
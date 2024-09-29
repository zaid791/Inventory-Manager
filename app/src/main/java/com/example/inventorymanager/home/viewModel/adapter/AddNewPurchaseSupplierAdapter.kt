package com.example.inventorymanager.home.viewModel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorymanager.R
import com.example.inventorymanager.databinding.ItemViewAddNewPurchaseSupplierBinding
import com.example.inventorymanager.home.model.NewProductSupplierDataClass

class AddNewPurchaseSupplierAdapter(private val items: List<NewProductSupplierDataClass>) : RecyclerView.Adapter<AddNewPurchaseSupplierAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemViewAddNewPurchaseSupplierBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewProductSupplierDataClass) {
            binding.editTextItemName.setText(item.itemName)
            binding.spinnerUnit.setSelection(getUnitPosition(item.unit))
            binding.editTextPriceUnit.setText(item.pricePerUnit)
            binding.editTextNoOfUnit.setText(item.noOfUnits)
            binding.editTextTax.setText(item.tax)
            binding.textViewTotalAmount.text = "Total Amount : ${calculateTotalAmount(item)}"

            val unitArrayAdapter = ArrayAdapter.createFromResource(
                binding.root.context,
                R.array.unit_array,
                android.R.layout.simple_spinner_item
            )
            unitArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerUnit.adapter = unitArrayAdapter
        }

        private fun getUnitPosition(unit: String): Int {
            val units = binding.spinnerUnit.context.resources.getStringArray(R.array.unit_array)
            return units.indexOf(unit)
        }

        private fun calculateTotalAmount(item: NewProductSupplierDataClass): String {
            // Placeholder logic to calculate the total amount
            // You might want to implement a more accurate calculation based on your data
            return "10,000" // Example amount
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewAddNewPurchaseSupplierBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
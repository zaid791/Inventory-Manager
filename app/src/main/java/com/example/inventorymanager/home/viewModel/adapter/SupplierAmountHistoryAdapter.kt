package com.example.inventorymanager.home.viewModel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorymanager.databinding.ItemViewSupplierMoneyHistoryBinding
import com.example.inventorymanager.home.model.SupplierAmount

class SupplierAmountHistoryAdapter(private val items: List<SupplierAmount>): RecyclerView.Adapter<SupplierAmountHistoryAdapter.ViewHolder>()  {
    inner class ViewHolder(private val binding: ItemViewSupplierMoneyHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SupplierAmount) {
            binding.tvDate.text = data.date
            binding.tvAmount.text = data.amountPaid
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewSupplierMoneyHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
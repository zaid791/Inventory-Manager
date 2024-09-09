package com.example.inventorymanager.home.viewModel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorymanager.databinding.ItemViewInventoryBinding
import com.example.inventorymanager.home.DataClass.InventoryItem

class InventoryAdapter(
    private val inventoryList: List<InventoryItem>
) : RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {

    inner class InventoryViewHolder(val binding: ItemViewInventoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemViewInventoryBinding.inflate(inflater, parent, false)
        return InventoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val item = inventoryList[position]
        holder.binding.apply {
            tvName.text = item.name
            tvStock.text = item.stock
            tvCategory.text = item.category
            tvPrice.text = item.price
            imageView.setImageResource(item.imageResId)
        }
    }

    override fun getItemCount(): Int {
        return inventoryList.size
    }
}

package com.example.inventorymanager.home.viewModel.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorymanager.databinding.ItemBuyerSellerBinding
import com.example.inventorymanager.home.model.response.TransactionModel

class BuyerSellerAdapter(
    private val items: List<TransactionModel>
) : RecyclerView.Adapter<BuyerSellerAdapter.YourViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YourViewHolder {
        val binding =
            ItemBuyerSellerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return YourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YourViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class YourViewHolder(private val binding: ItemBuyerSellerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: TransactionModel) {
            binding.apply {
                tvName.text = item.personName
                tvAmountPaid.text = "₹${item.amountPaid}"
                tvAmountPending.text = "₹${item.amountPending}"
                tvContact.text = "${item.contactNumber}"
            }
        }
    }
}
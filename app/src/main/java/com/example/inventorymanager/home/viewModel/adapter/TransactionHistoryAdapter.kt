package com.example.inventorymanager.home.viewModel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorymanager.common.CommonViewModel
import com.example.inventorymanager.databinding.ItemTransactionDetailsBinding
import com.example.inventorymanager.databinding.ItemTransactionHistoryBinding
import com.example.inventorymanager.home.model.Items
import com.example.inventorymanager.home.model.TransactionModel

class TransactionHistoryAdapter(
    private val transactions: List<TransactionModel>,
    private val commonViewModel: CommonViewModel
) :
    RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemTransactionHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: TransactionModel) {
            binding.tvTransactionDateTime.text =
                commonViewModel.formatDateTimeToReadableString(transaction.dateTime)
            binding.rvTransactions.apply {
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(binding.root.context)
                adapter = TransactionItemsAdapter(transaction.items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}

class TransactionItemsAdapter(private val items: List<Items>) :
    RecyclerView.Adapter<TransactionItemsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemTransactionDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Items) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

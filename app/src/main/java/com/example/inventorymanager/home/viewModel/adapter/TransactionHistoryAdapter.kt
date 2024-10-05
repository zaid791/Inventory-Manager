package com.example.inventorymanager.home.viewModel.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorymanager.common.CommonViewModel
import com.example.inventorymanager.common.Messages
import com.example.inventorymanager.databinding.ItemViewSupplierMoneyHistoryBinding
import com.example.inventorymanager.home.model.TransactionModel

@RequiresApi(Build.VERSION_CODES.O)
class TransactionHistoryAdapter(
    private val transactions: List<TransactionModel>,
    private val commonViewModel: CommonViewModel
) :
    RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemViewSupplierMoneyHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: TransactionModel) {
            binding.tvDate.text =
                commonViewModel.formatDateTimeToReadableString(transaction.dateTime)
            binding.tvAmount.text = Messages.getCurrencyString(transaction.amountPaid)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewSupplierMoneyHistoryBinding.inflate(
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

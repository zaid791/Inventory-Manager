package com.example.inventorymanager.home.viewModel.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorymanager.R
import com.example.inventorymanager.common.Actions
import com.example.inventorymanager.common.Messages
import com.example.inventorymanager.databinding.ItemBuyerSellerBinding
import com.example.inventorymanager.home.model.UserDetailsModel

class BuyerSellerAdapter(
    private val items: List<UserDetailsModel>,
    private val onAction: (UserDetailsModel, Actions) -> Unit
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
        fun bind(userDetailsModel: UserDetailsModel) {
            binding.apply {
                tvName.text = if (userDetailsModel.alias.isNullOrEmpty()) Messages.getFullName(
                    userDetailsModel.firstName,
                    userDetailsModel.lastName
                ) else userDetailsModel.alias
                tvAmountPaid.text =
                    Messages.getCurrencyString(userDetailsModel.totalAmountPaidAllTransactions)
                tvAmountPending.text =
                    Messages.getCurrencyString(userDetailsModel.totalAmountPendingAllTransactions)
                tvContact.text = userDetailsModel.mobileNumber.toString()
            }
            binding.btnOptions.setOnClickListener { view ->
                showPopupMenu(view, userDetailsModel)
            }
        }

        private fun showPopupMenu(view: View?, userDetailsModel: UserDetailsModel) {
            // Create a PopupMenu
            val popupMenu = PopupMenu(binding.root.context, view)
            popupMenu.menuInflater.inflate(R.menu.menu_popup, popupMenu.menu)

            // Set a click listener for menu items
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.actionView -> {
                        onAction(userDetailsModel, Actions.View)
                        true
                    }

                    R.id.actionEdit -> {
                        onAction(userDetailsModel, Actions.Edit)
                        true
                    }

                    R.id.actionDelete -> {
                        onAction(userDetailsModel, Actions.Delete)
                        true
                    }

                    else -> false
                }
            }

            // Show the PopupMenu
            popupMenu.show()
        }
    }
}
package com.example.inventorymanager.home.viewModel.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
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
        fun bind(item: UserDetailsModel) {
            binding.apply {
                tvName.text = if (item.alias.isNullOrEmpty()) Messages.getFullName(
                    item.firstName,
                    item.lastName
                ) else item.alias
                tvAmountPaid.text = "₹"
                tvAmountPending.text = "₹"
                tvContact.text = item.mobileNumber.toString()
            }
            binding.btnOptions.setOnClickListener { view ->
                showPopupMenu(view, item)
            }
        }

        private fun showPopupMenu(view: View?, item: UserDetailsModel) {
            // Create a PopupMenu
            val popupMenu = PopupMenu(binding.root.context, view)
            popupMenu.menuInflater.inflate(R.menu.menu_popup, popupMenu.menu)

            // Set a click listener for menu items
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.actionView -> {
                        onAction(item, Actions.View)
                        true
                    }

                    R.id.actionEdit -> {
                        onAction(item, Actions.Edit)
                        true
                    }

                    R.id.actionDelete -> {
                        onAction(item, Actions.Delete)
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
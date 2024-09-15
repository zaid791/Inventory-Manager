package com.example.inventorymanager.home.viewModel.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorymanager.common.Messages
import com.example.inventorymanager.databinding.ItemBuyerSellerBinding
import com.example.inventorymanager.details.model.dataClass.UserDetailsModel
import com.example.inventorymanager.home.model.response.TransactionModel

class BuyerSellerAdapter(
    private val items: List<UserDetailsModel>,
    private val onItemClick: (TransactionModel) -> Unit
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
                tvName.text = if (item.alias.isNullOrEmpty()) Messages.getFullName(item.firstName, item.lastName) else item.alias
                tvAmountPaid.text = "₹"
                tvAmountPending.text = "₹"
                tvContact.text = "${item.mobileNumber}"
            }

//            binding.root.setOnClickListener {
//                onItemClick(item)
//            }

            binding.iconEdit.setOnClickListener { view ->
                // Create a PopupMenu
                val popupMenu = PopupMenu(binding.root.context, view)

                // Inflate the menu resource with Edit and View options
                popupMenu.menu.add(0, 1, 0, "Edit")
                popupMenu.menu.add(0, 2, 1, "View")

                // Set custom background
//                val popupBackground = binding.root.context.resources.getDrawable(R.drawable.rounded_popup_background)
//                ViewCompat.setBackground(popupMenu, popupBackground)


                // Set a click listener for menu items
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        1 -> {
                            // Handle Edit action
                            Toast.makeText(binding.root.context, "Edit clicked", Toast.LENGTH_SHORT)
                                .show()
                            true
                        }

                        2 -> {
                            // Handle View action
                            Toast.makeText(binding.root.context, "View clicked", Toast.LENGTH_SHORT)
                                .show()
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
}
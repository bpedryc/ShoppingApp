package com.haxos.shoppingapp.shoppingcarts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.haxos.shoppingapp.databinding.ItemShoppingcartBinding
import com.haxos.shoppingapp.shoppingcarts.ShoppingCartsAdapter.ViewHolder

class ShoppingCartsAdapter(private val viewModel: ShoppingCartsViewModel) :
    ListAdapter<ShoppingCart, ViewHolder>(ShoppingCartDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, postion: Int) {
        val item = getItem(postion)
        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemShoppingcartBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ShoppingCartsViewModel, item: ShoppingCart) {
            binding.viewmodel = viewModel
            binding.shoppingcart = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemShoppingcartBinding
                    .inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }
}

class ShoppingCartDiffCallback : DiffUtil.ItemCallback<ShoppingCart>() {
    override fun areItemsTheSame(oldItem: ShoppingCart, newItem: ShoppingCart): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShoppingCart, newItem: ShoppingCart): Boolean {
        return oldItem == newItem
    }
}
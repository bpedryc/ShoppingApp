package com.haxos.shoppingapp.shoppinglists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.haxos.shoppingapp.databinding.ItemShoppinglistBinding
import com.haxos.shoppingapp.shoppinglists.ShoppingListsAdapter.ViewHolder

class ShoppingListsAdapter(private val viewModel: ShoppingListsViewModel) :
    ListAdapter<ShoppingList, ViewHolder>(ShoppingListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, postion: Int) {
        val item = getItem(postion)
        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemShoppinglistBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ShoppingListsViewModel, item: ShoppingList) {
            binding.viewmodel = viewModel
            binding.shoppinglist = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemShoppinglistBinding
                    .inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }
}

class ShoppingListDiffCallback : DiffUtil.ItemCallback<ShoppingList>() {
    override fun areItemsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
        return oldItem == newItem
    }
}
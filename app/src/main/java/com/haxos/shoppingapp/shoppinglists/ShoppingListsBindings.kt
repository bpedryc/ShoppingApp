package com.haxos.shoppingapp.shoppinglists

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.haxos.shoppingapp.data.ShoppingList

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<ShoppingList>) {
    (listView.adapter as ShoppingListsAdapter).submitList(items)
}
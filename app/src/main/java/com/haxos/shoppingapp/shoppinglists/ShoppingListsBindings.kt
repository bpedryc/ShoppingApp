package com.haxos.shoppingapp.shoppinglists

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<ShoppingList>) {
    (listView.adapter as ShoppingListsAdapter).submitList(items)
}
package com.haxos.shoppingapp.shoppinglistdetails

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Grocery>) {
    (listView.adapter as GroceriesAdapter).submitList(items)
}
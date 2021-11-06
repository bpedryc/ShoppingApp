package com.haxos.shoppingapp.shoppingcarts

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<ShoppingCart>) {
    (listView.adapter as ShoppingCartsAdapter).submitList(items)
}
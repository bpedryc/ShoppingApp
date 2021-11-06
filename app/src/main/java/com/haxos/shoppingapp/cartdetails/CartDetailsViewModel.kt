package com.haxos.shoppingapp.cartdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haxos.shoppingapp.shoppingcarts.ShoppingCartDao
import javax.inject.Inject

class CartDetailsViewModel @Inject constructor(
    private val shoppingCartDao: ShoppingCartDao
) : ViewModel() {

    private val _groceries = MutableLiveData<List<Grocery>>().apply { value = emptyList() }
    val groceries: LiveData<List<Grocery>> = _groceries

}
package com.haxos.shoppingapp.cartdetails

import androidx.lifecycle.ViewModel
import com.haxos.shoppingapp.shoppingcarts.ShoppingCartDao
import javax.inject.Inject

class CartDetailsViewModel @Inject constructor(
    private val shoppingCartDao: ShoppingCartDao
) : ViewModel()
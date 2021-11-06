package com.haxos.shoppingapp.shoppingCarts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haxos.shoppingapp.data.Result
import com.haxos.shoppingapp.data.Result.Success
import com.haxos.shoppingapp.data.Result.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShoppingCartsViewModel @Inject constructor(
    private val shoppingCartDao: ShoppingCartDao
): ViewModel() {

    private val _shoppingCarts = MutableLiveData<List<ShoppingCart>>().apply { value = emptyList() }
    val shoppingCarts: LiveData<List<ShoppingCart>> = _shoppingCarts

    init {
        viewModelScope.launch {

            var carts = getCarts() ?: return@launch
            if (carts.isEmpty()) {
                val testCarts = insertTestsCarts()
                carts = testCarts
            }

            _shoppingCarts.value = carts
        }
    }

    fun openCart(id: String) {
        TODO()
    }

    private suspend fun insertTestsCarts(): List<ShoppingCart> = withContext(Dispatchers.IO) {
        val cart1 = ShoppingCart("Monday groceries")
        val cart2 = ShoppingCart("Saturday big shopping")
        shoppingCartDao.insertCart(cart1)
        shoppingCartDao.insertCart(cart2)
        return@withContext listOf(cart1, cart2)
    }

    private suspend fun getCarts() : List<ShoppingCart>? {
        val result = fetchCarts()
        if (result is Success) {
            return result.data
        }
        return null
    }

    private suspend fun fetchCarts(): Result<List<ShoppingCart>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Success(shoppingCartDao.getCarts())
        } catch (e: Exception) {
            Error(e)
        }
    }

}
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
            val result = loadCarts()
            if (result is Success) {
                _shoppingCarts.value = result.data
            }
        }
    }

    private suspend fun loadCarts(): Result<List<ShoppingCart>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Success(shoppingCartDao.getCarts())
        } catch (e: Exception) {
            Error(e)
        }
    }

}
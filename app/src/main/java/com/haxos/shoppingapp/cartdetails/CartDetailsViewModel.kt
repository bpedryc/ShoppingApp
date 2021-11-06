package com.haxos.shoppingapp.cartdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haxos.shoppingapp.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartDetailsViewModel @Inject constructor(
    private val groceryDao: GroceryDao
) : ViewModel() {

    private val _groceries = MutableLiveData<List<Grocery>>().apply { value = emptyList() }
    val groceries: LiveData<List<Grocery>> = _groceries

    fun start(shoppingCartId: String) = viewModelScope.launch {
        var groceries = getGroceries(shoppingCartId) ?: return@launch
        if (groceries.isEmpty()) {
            groceries = insertTestGroceries()
        }
        _groceries.value = groceries
    }

    private suspend fun insertTestGroceries(): List<Grocery> = withContext(Dispatchers.IO) {
        val grocery1 = Grocery("Tomato")
        val grocery2 = Grocery("Apple pie")
        groceryDao.insertGrocery(grocery1)
        groceryDao.insertGrocery(grocery2)
        return@withContext listOf(grocery1, grocery2)
    }

    private suspend fun getGroceries(cartId: String) : List<Grocery>? {
        val result = fetchGroceries(cartId)
        if (result is Result.Success) {
            return result.data
        }
        println(result)
        return null
    }

    private suspend fun fetchGroceries(cartId: String): Result<List<Grocery>>
    = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(groceryDao.getGroceriesByCartId(cartId))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}
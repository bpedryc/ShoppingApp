package com.haxos.shoppingapp.shoppinglistdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haxos.shoppingapp.data.Grocery
import com.haxos.shoppingapp.data.Result
import com.haxos.shoppingapp.data.database.GroceryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShoppingListDetailsViewModel @Inject constructor(
    private val groceryDao: GroceryDao
) : ViewModel() {

    private val _groceries = MutableLiveData<List<Grocery>>().apply { value = emptyList() }
    val groceries: LiveData<List<Grocery>> = _groceries

    fun start(shoppingListId: String) = viewModelScope.launch {
        var groceries = getGroceries(shoppingListId) ?: return@launch
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

    private suspend fun getGroceries(shoppingListId: String) : List<Grocery>? {
        val result = fetchGroceries(shoppingListId)
        if (result is Result.Success) {
            return result.data
        }
        println(result)
        return null
    }

    private suspend fun fetchGroceries(shoppingListId: String): Result<List<Grocery>>
    = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(groceryDao.getGroceriesByShoppingListId(shoppingListId))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}
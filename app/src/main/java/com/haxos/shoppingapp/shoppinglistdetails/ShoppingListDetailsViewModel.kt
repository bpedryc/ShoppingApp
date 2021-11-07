package com.haxos.shoppingapp.shoppinglistdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haxos.shoppingapp.data.Grocery
import com.haxos.shoppingapp.data.Result
import com.haxos.shoppingapp.data.database.GroceryDao
import com.haxos.shoppingapp.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShoppingListDetailsViewModel @Inject constructor(
    private val groceryDao: GroceryDao
) : ViewModel() {

    private val _groceries = MutableLiveData<List<Grocery>>().apply { value = emptyList() }
    val groceries: LiveData<List<Grocery>> = _groceries

    private val _createdGroceryEvent = MutableLiveData<Event<Unit>>()
    val createdGroceryEvent: LiveData<Event<Unit>> = _createdGroceryEvent

    fun start(shoppingListId: String) = viewModelScope.launch {
        val groceries = getGroceries(shoppingListId) ?: return@launch
        _groceries.value = groceries
    }

    fun createGrocery(shoppingListId: String, groceryName: String) = viewModelScope.launch {
        val newGrocery = Grocery(groceryName, shoppingListId)
        insertGrocery(newGrocery)
        _createdGroceryEvent.value = Event(Unit)
    }

    private suspend fun getGroceries(shoppingListId: String) : List<Grocery>? {
        val result = fetchGroceries(shoppingListId)
        if (result is Result.Success) {
            return result.data
        }
        println(result)
        return null
    }

    private suspend fun insertGrocery(grocery: Grocery) = withContext(Dispatchers.IO) {
        groceryDao.insertGrocery(grocery)
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
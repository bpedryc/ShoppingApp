package com.haxos.shoppingapp.shoppinglistdetails

import androidx.lifecycle.*
import com.haxos.shoppingapp.data.Grocery
import com.haxos.shoppingapp.data.Result
import com.haxos.shoppingapp.data.ShoppingList
import com.haxos.shoppingapp.data.database.GroceryDao
import com.haxos.shoppingapp.data.database.ShoppingListDao
import com.haxos.shoppingapp.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShoppingListDetailsViewModel @Inject constructor(
    private val shoppingListDao: ShoppingListDao,
    private val groceryDao: GroceryDao
) : ViewModel() {

    private val _groceries = MutableLiveData<List<Grocery>>().apply { value = emptyList() }
    val groceries: LiveData<List<Grocery>> = _groceries

    private val _archived = MutableLiveData<Boolean>()
    val archived: LiveData<Boolean> = _archived

    private val _createdGroceryEvent = MutableLiveData<Event<Unit>>()
    val createdGroceryEvent: LiveData<Event<Unit>> = _createdGroceryEvent

    private val _deletedGroceryEvent = MutableLiveData<Event<Unit>>()
    val deletedGroceryEvent: LiveData<Event<Unit>> = _deletedGroceryEvent

    val empty: LiveData<Boolean> = Transformations.map(_groceries) {
        it.isEmpty()
    }

    fun start(shoppingListId: String) = viewModelScope.launch {
        val groceriesResult = async { fetchGroceries(shoppingListId) }
        val shoppingListResult = async { fetchShoppingList(shoppingListId) }

        val groceries = handleResult(groceriesResult.await()) ?: return@launch
        val shoppingList = handleResult(shoppingListResult.await()) ?:return@launch

        _groceries.value = groceries
        _archived.value = shoppingList.archived
    }

    fun createGrocery(shoppingListId: String, groceryName: String) = viewModelScope.launch {
        val newGrocery = Grocery(groceryName, shoppingListId)
        insertGrocery(newGrocery)
        _createdGroceryEvent.value = Event(Unit)
    }

    fun deleteGrocery(groceryId: String) = viewModelScope.launch {
        groceryDao.deleteGroceryById(groceryId)
        _deletedGroceryEvent.value = Event(Unit)
    }

    private fun <T>handleResult(result: Result<T>): T? {
        if (result is Result.Success) {
            return result.data
        }
        println(result) //Log the error
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

    private suspend fun fetchShoppingList(shoppingListId: String): Result<ShoppingList>
    = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(shoppingListDao.getShoppingListById(shoppingListId))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
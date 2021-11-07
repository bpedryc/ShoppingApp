package com.haxos.shoppingapp.shoppinglists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haxos.shoppingapp.Event
import com.haxos.shoppingapp.data.Result
import com.haxos.shoppingapp.data.Result.Success
import com.haxos.shoppingapp.data.Result.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShoppingListsViewModel @Inject constructor(
    private val shoppingListDao: ShoppingListDao
): ViewModel() {

    private val _shoppingLists = MutableLiveData<List<ShoppingList>>().apply { value = emptyList() }
    val shoppingLists: LiveData<List<ShoppingList>> = _shoppingLists

    private val _openShoppingListEvent = MutableLiveData<Event<String>>()
    val openShoppingListEvent: LiveData<Event<String>> = _openShoppingListEvent

    init {
        viewModelScope.launch {

            var shoppingLists = getShoppingLists() ?: return@launch
            if (shoppingLists.isEmpty()) {
                val testShoppingLists = insertTestsShoppingLists()
                shoppingLists = testShoppingLists
            }

            _shoppingLists.value = shoppingLists
        }
    }

    fun openShoppingList(shoppingListId: String) {
        _openShoppingListEvent.value = Event(shoppingListId)
    }

    private suspend fun insertTestsShoppingLists(): List<ShoppingList> = withContext(Dispatchers.IO) {
        val shoppingList1 = ShoppingList("Monday groceries")
        val shoppingList2 = ShoppingList("Saturday big shopping")
        shoppingListDao.insertShoppingList(shoppingList1)
        shoppingListDao.insertShoppingList(shoppingList2)
        return@withContext listOf(shoppingList1, shoppingList2)
    }

    private suspend fun getShoppingLists() : List<ShoppingList>? {
        val result = fetchShoppingLists()
        if (result is Success) {
            return result.data
        }
        println(result)
        return null
    }

    private suspend fun fetchShoppingLists(): Result<List<ShoppingList>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Success(shoppingListDao.getShoppingLists())
        } catch (e: Exception) {
            Error(e)
        }
    }

}
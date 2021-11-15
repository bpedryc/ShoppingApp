package com.haxos.shoppingapp.shoppinglistdetails

import androidx.lifecycle.*
import com.haxos.shoppingapp.R
import com.haxos.shoppingapp.data.models.Grocery
import com.haxos.shoppingapp.data.Result
import com.haxos.shoppingapp.data.datasources.ShoppingListDetailsDataSource
import com.haxos.shoppingapp.data.models.ShoppingList
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingListDetailsViewModel @Inject constructor(
    private val dataSource: ShoppingListDetailsDataSource
) : ViewModel() {

    private val _groceries = MutableLiveData<List<Grocery>>().apply { value = emptyList() }
    val groceries: LiveData<List<Grocery>> = _groceries

    private val _shoppingList = MutableLiveData<ShoppingList>()
    val shoppingList: LiveData<ShoppingList> = _shoppingList

    private val _errorMessage = MutableLiveData<Int>()
    val errorMessage: LiveData<Int> = _errorMessage

    private val shoppingListId: String?
        get() = _shoppingList.value?.id

    val showNoGroceriesLabel: LiveData<Boolean> = Transformations.map(_groceries) {
        it.isEmpty() && _shoppingList.value != null
    }

    val archived: LiveData<Boolean> = Transformations.map(shoppingList) {
        it.archived
    }

    fun start(shoppingListId: String) = viewModelScope.launch {
        val groceriesResult = async { dataSource.getShoppingListGroceries(shoppingListId) }
        val shoppingListResult = async { dataSource.getShoppingList(shoppingListId) }

        val groceries = handleResult(
            result = groceriesResult.await(),
            messageOnError = R.string.error_message_groceries_fetch
        ) ?: return@launch

        val shoppingList = handleResult(
            result = shoppingListResult.await(),
            messageOnError = R.string.error_message_shoppinglist_fetch
        ) ?: return@launch

        _shoppingList.value = shoppingList
        _groceries.value = groceries
    }

    fun createGrocery(shoppingListId: String, groceryName: String) = viewModelScope.launch {
        val newGrocery = Grocery(groceryName, shoppingListId)
        dataSource.insertGrocery(newGrocery)
        refresh()
    }

    fun deleteGrocery(groceryId: String) = viewModelScope.launch {
        dataSource.deleteGroceryById(groceryId)
        refresh()
    }

    private fun refresh() {
        shoppingListId?.let { start(it) }
    }

    private fun <T>handleResult(
        result: Result<T>,
        messageOnError: Int = R.string.error_message_default
    ): T? {
        if (result is Result.Success) {
            return result.data
        }
        _errorMessage.value = messageOnError
        println(result) //Log the error
        return null
    }
}
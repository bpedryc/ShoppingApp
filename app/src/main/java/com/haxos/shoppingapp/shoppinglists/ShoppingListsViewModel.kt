package com.haxos.shoppingapp.shoppinglists

import androidx.lifecycle.*
import com.haxos.shoppingapp.R
import com.haxos.shoppingapp.data.Result.Success
import com.haxos.shoppingapp.data.models.ShoppingList
import com.haxos.shoppingapp.data.datasources.ShoppingListsDataSource
import com.haxos.shoppingapp.utils.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingListsViewModel @Inject constructor(
    private val dataSource: ShoppingListsDataSource
): ViewModel() {

    private val _shoppingLists = MutableLiveData<List<ShoppingList>>().apply { value = emptyList() }
    val shoppingLists: LiveData<List<ShoppingList>> = _shoppingLists

    private val _openShoppingListEvent = MutableLiveData<Event<String>>()
    val openShoppingListEvent: LiveData<Event<String>> = _openShoppingListEvent

    private val _noListsLabel = MutableLiveData<Int>()
    val noListsLabel: LiveData<Int> = _noListsLabel

    val empty: LiveData<Boolean> = Transformations.map(_shoppingLists) {
        it.isEmpty()
    }

    private var currentFilter = ShoppingListFilter.ACTIVE

    init {
        setFilter(currentFilter)
    }

    fun loadShoppingLists() = viewModelScope.launch {

        val shoppingLists = getShoppingLists() ?: return@launch
        val shoppingListsToShow = ArrayList<ShoppingList>()

        for (shoppingList in shoppingLists) {
            when (currentFilter) {
                ShoppingListFilter.ACTIVE -> if (!shoppingList.archived) {
                    shoppingListsToShow.add(shoppingList)
                }
                ShoppingListFilter.ARCHIVED -> if (shoppingList.archived) {
                    shoppingListsToShow.add(shoppingList)
                }
            }
        }

        _shoppingLists.value = shoppingListsToShow.sortedByDescending { it.createdTime }
    }

    fun setFilter(filter: ShoppingListFilter) {
        currentFilter = filter
        when (filter) {
            ShoppingListFilter.ACTIVE -> _noListsLabel.value = R.string.no_active_shopping_lists
            ShoppingListFilter.ARCHIVED -> _noListsLabel.value = R.string.no_archived_shopping_lists
        }
    }

    fun openShoppingList(shoppingListId: String) {
        _openShoppingListEvent.value = Event(shoppingListId)
    }

    fun createShoppingList(name: String) = viewModelScope.launch {
        val newShoppingList = ShoppingList(name)
        dataSource.saveShoppingList(newShoppingList)
        loadShoppingLists()
    }

    fun archiveShoppingList(shoppingListId: String) = viewModelScope.launch {
        dataSource.archiveShoppingList(shoppingListId)
        loadShoppingLists()
    }

    private suspend fun getShoppingLists() : List<ShoppingList>? {
        val result = dataSource.getShoppingLists()
        if (result is Success) {
            return result.data
        }
        println(result)
        return null
    }
}
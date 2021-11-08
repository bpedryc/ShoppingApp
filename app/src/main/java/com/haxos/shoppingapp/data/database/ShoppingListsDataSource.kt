package com.haxos.shoppingapp.data.database

import com.haxos.shoppingapp.data.Result
import com.haxos.shoppingapp.data.ShoppingList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShoppingListsDataSource constructor(
    val shoppingListDao: ShoppingListDao,
){
    suspend fun getShoppingLists(): Result<List<ShoppingList>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(shoppingListDao.getShoppingLists())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun saveShoppingList(shoppingList: ShoppingList) = withContext(Dispatchers.IO) {
        shoppingListDao.insertShoppingList(shoppingList)
    }

    suspend fun archiveShoppingList(shoppingListId: String) = withContext(Dispatchers.IO) {
        shoppingListDao.updateArchived(shoppingListId, true)
    }
}
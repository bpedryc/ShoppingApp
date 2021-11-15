package com.haxos.shoppingapp.data.datasources

import com.haxos.shoppingapp.data.Result
import com.haxos.shoppingapp.data.database.GroceryDao
import com.haxos.shoppingapp.data.database.ShoppingListDao
import com.haxos.shoppingapp.data.models.Grocery
import com.haxos.shoppingapp.data.models.ShoppingList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShoppingListDetailsDataSource (
    private val shoppingListDao: ShoppingListDao,
    private val groceryDao: GroceryDao
){

    suspend fun getShoppingListGroceries(shoppingListId: String): Result<List<Grocery>>
            = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(groceryDao.getGroceriesByShoppingListId(shoppingListId))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getShoppingList(shoppingListId: String): Result<ShoppingList>
            = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(shoppingListDao.getShoppingListById(shoppingListId))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun insertGrocery(grocery: Grocery) = withContext(Dispatchers.IO) {
        groceryDao.insertGrocery(grocery)
    }

    suspend fun deleteGroceryById(groceryId: String) = withContext(Dispatchers.IO) {
        groceryDao.deleteGroceryById(groceryId)
    }
}
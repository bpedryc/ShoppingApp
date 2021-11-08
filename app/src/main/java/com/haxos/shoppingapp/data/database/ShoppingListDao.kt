package com.haxos.shoppingapp.data.database

import androidx.room.*
import com.haxos.shoppingapp.data.ShoppingList

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM shopping_lists")
    suspend fun getShoppingLists(): List<ShoppingList>

    @Query("SELECT * FROM shopping_lists WHERE id = :shoppingListId")
    suspend fun getShoppingListById(shoppingListId: String): ShoppingList

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingList(shoppingList: ShoppingList)

    @Query("UPDATE shopping_lists SET archived = :archived WHERE id = :shoppingListId")
    suspend fun updateArchived(shoppingListId: String, archived: Boolean)

    @Query("DELETE FROM shopping_lists WHERE id = :shoppingListId")
    suspend fun deleteShoppingListById(shoppingListId: String)

    @Query("DELETE FROM shopping_lists")
    suspend fun deleteShoppingLists()
}
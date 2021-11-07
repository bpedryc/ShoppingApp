package com.haxos.shoppingapp.data.database

import androidx.room.*
import com.haxos.shoppingapp.data.Grocery

@Dao
interface GroceryDao {

    @Query("SELECT * FROM groceries")
    suspend fun getGroceries(): List<Grocery>

    @Query("SELECT * FROM groceries WHERE id = :groceryId")
    suspend fun getGroceryById(groceryId: String): Grocery

    @Query("SELECT * FROM groceries WHERE shopping_list_id = :shoppingListId")
    suspend fun getGroceriesByShoppingListId(shoppingListId: String) : List<Grocery>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrocery(grocery: Grocery)

    @Update
    suspend fun updateGrocery(grocery: Grocery)

    @Query("DELETE FROM groceries WHERE id = :groceryId")
    suspend fun deleteGroceryById(groceryId: String)

    @Query("DELETE FROM groceries")
    suspend fun deleteGroceries()
}
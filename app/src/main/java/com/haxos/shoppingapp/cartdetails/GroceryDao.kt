package com.haxos.shoppingapp.cartdetails

import androidx.room.*

@Dao
interface GroceryDao {

    @Query("SELECT * FROM groceries")
    suspend fun getGroceries(): List<Grocery>

    @Query("SELECT * FROM groceries WHERE id = :groceryId")
    suspend fun getGroceryById(groceryId: String): Grocery

    @Query("SELECT * FROM groceries WHERE shopping_cart_id = :shoppingCartId")
    suspend fun getGroceriesByCartId(shoppingCartId: String) : List<Grocery>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrocery(grocery: Grocery)

    @Update
    suspend fun updateGrocery(grocery: Grocery)

    @Query("DELETE FROM groceries WHERE id = :groceryId")
    suspend fun deleteGroceryById(groceryId: String)

    @Query("DELETE FROM groceries")
    suspend fun deleteGroceries()
}
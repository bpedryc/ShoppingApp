package com.haxos.shoppingapp.shoppingCarts

import androidx.room.*

@Dao
interface ShoppingCartDao {

    @Query("SELECT * FROM shopping_carts")
    suspend fun getCarts(): List<ShoppingCart>

    @Query("SELECT * FROM shopping_carts WHERE id = :cartId")
    suspend fun getCartById(cartId: String): ShoppingCart

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: ShoppingCart)

    @Update
    suspend fun updateCart(cart: ShoppingCart)

    @Query("DELETE FROM shopping_carts WHERE id = :cartId")
    suspend fun deleteCartById(cartId: String)

    @Query("DELETE FROM shopping_carts")
    suspend fun deleteCarts()
}
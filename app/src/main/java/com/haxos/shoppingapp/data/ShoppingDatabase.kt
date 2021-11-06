package com.haxos.shoppingapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haxos.shoppingapp.cartdetails.Grocery
import com.haxos.shoppingapp.cartdetails.GroceryDao
import com.haxos.shoppingapp.shoppingcarts.ShoppingCart
import com.haxos.shoppingapp.shoppingcarts.ShoppingCartDao

@Database(entities = [ShoppingCart::class, Grocery::class], version = 1, exportSchema = false)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun shoppingCartDao() : ShoppingCartDao

    abstract fun groceryDao() : GroceryDao

}
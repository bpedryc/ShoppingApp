package com.haxos.shoppingapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haxos.shoppingapp.shoppinglistdetails.Grocery
import com.haxos.shoppingapp.shoppinglistdetails.GroceryDao
import com.haxos.shoppingapp.shoppinglists.ShoppingList
import com.haxos.shoppingapp.shoppinglists.ShoppingListDao

@Database(entities = [ShoppingList::class, Grocery::class], version = 1, exportSchema = false)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun shoppingListDao() : ShoppingListDao

    abstract fun groceryDao() : GroceryDao

}
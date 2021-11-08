package com.haxos.shoppingapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.haxos.shoppingapp.data.models.Grocery
import com.haxos.shoppingapp.data.models.ShoppingList

@Database(entities = [ShoppingList::class, Grocery::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun shoppingListDao() : ShoppingListDao

    abstract fun groceryDao() : GroceryDao

}
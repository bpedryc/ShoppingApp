package com.haxos.shoppingapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haxos.shoppingapp.shoppingcarts.ShoppingCart
import com.haxos.shoppingapp.shoppingcarts.ShoppingCartDao

@Database(entities = [ShoppingCart::class], version = 1, exportSchema = false)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun shoppingCartDao() : ShoppingCartDao

}
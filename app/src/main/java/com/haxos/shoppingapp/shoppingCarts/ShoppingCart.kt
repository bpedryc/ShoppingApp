package com.haxos.shoppingapp.shoppingCarts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "shopping_carts")
data class ShoppingCart @JvmOverloads constructor(
    @ColumnInfo(name = "name") var name: String = "",
    @PrimaryKey @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString()
)

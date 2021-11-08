/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haxos.shoppingapp.di

import android.content.Context
import androidx.room.Room
import com.haxos.shoppingapp.data.database.*
import com.haxos.shoppingapp.data.datasources.ShoppingListDetailsDataSource
import com.haxos.shoppingapp.data.datasources.ShoppingListsDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
object ApplicationModule {

    @Singleton
    @Provides
    fun provideDataBase(context: Context): ShoppingDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ShoppingDatabase::class.java,
            "shopping.db"
        )
            .createFromAsset("initial_shopping.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideShoppingListDao(database: ShoppingDatabase) : ShoppingListDao {
        return database.shoppingListDao()
    }

    @Singleton
    @Provides
    fun provideGroceryDao(database: ShoppingDatabase) : GroceryDao {
        return database.groceryDao()
    }

    @Singleton
    @Provides
    fun provideShoppingListsDataSource(shoppingListDao: ShoppingListDao): ShoppingListsDataSource {
        return ShoppingListsDataSource(shoppingListDao)
    }

    @Singleton
    @Provides
    fun provideShoppingListDetailsDataSource(
        shoppingListDao: ShoppingListDao,
        groceryDao: GroceryDao
    ) : ShoppingListDetailsDataSource {
         return ShoppingListDetailsDataSource(shoppingListDao, groceryDao)
    }
}
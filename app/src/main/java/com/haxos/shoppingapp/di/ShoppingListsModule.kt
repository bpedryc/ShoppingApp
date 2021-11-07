package com.haxos.shoppingapp.di

import androidx.lifecycle.ViewModel
import com.haxos.shoppingapp.shoppinglists.ShoppingListsFragment
import com.haxos.shoppingapp.shoppinglists.ShoppingListsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ShoppingListsModule {
    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    internal abstract fun shoppingListsFragment(): ShoppingListsFragment

    @Binds
    @IntoMap
    @ViewModelKey(ShoppingListsViewModel::class)
    abstract fun bindViewModel(viewmodel: ShoppingListsViewModel): ViewModel

}
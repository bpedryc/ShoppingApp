package com.haxos.shoppingapp.di

import androidx.lifecycle.ViewModel
import com.haxos.shoppingapp.shoppinglistdetails.ShoppingListDetailsFragment
import com.haxos.shoppingapp.shoppinglistdetails.ShoppingListDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ShoppingListDetailsModule {

    @ContributesAndroidInjector(modules =[
        ViewModelBuilder::class
    ])
    internal abstract fun shoppingListDetailsFragment(): ShoppingListDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(ShoppingListDetailsViewModel::class)
    abstract fun bindViewModel(viewmodel: ShoppingListDetailsViewModel): ViewModel
}
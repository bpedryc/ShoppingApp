package com.haxos.shoppingapp.di

import androidx.lifecycle.ViewModel
import com.haxos.shoppingapp.shoppingcarts.ShoppingCartsFragment
import com.haxos.shoppingapp.shoppingcarts.ShoppingCartsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ShoppingCartsModule {
    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    internal abstract fun shoppingCartsFragment(): ShoppingCartsFragment

    @Binds
    @IntoMap
    @ViewModelKey(ShoppingCartsViewModel::class)
    abstract fun bindViewModel(viewmodel: ShoppingCartsViewModel): ViewModel

}
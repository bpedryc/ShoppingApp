package com.haxos.shoppingapp.di

import androidx.lifecycle.ViewModel
import com.haxos.shoppingapp.cartdetails.CartDetailsFragment
import com.haxos.shoppingapp.cartdetails.CartDetailsViewModel
import com.haxos.shoppingapp.shoppingcarts.ShoppingCartsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class CartDetailsModule {

    @ContributesAndroidInjector(modules =[
        ViewModelBuilder::class
    ])
    internal abstract fun cartDetailsFragment(): CartDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(CartDetailsViewModel::class)
    abstract fun bindViewModel(viewmodel: CartDetailsViewModel): ViewModel
}
package com.haxos.shoppingapp.di

import com.haxos.shoppingapp.CartDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CartDetailsModule {

    @ContributesAndroidInjector(modules =[
        ViewModelBuilder::class
    ])
    internal abstract fun cartDetailsFragment(): CartDetailsFragment
}
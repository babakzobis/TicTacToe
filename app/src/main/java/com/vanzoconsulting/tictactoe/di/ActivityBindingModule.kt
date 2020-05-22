package com.vanzoconsulting.tictactoe.di

import com.vanzoconsulting.tictactoe.ui.BoardActivity
import com.vanzoconsulting.tictactoe.ui.BoardModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [BoardModule::class])
    abstract fun boardActivity(): BoardActivity
}
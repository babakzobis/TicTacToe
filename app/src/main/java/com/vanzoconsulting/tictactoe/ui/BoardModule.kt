package com.vanzoconsulting.tictactoe.ui

import com.vanzoconsulting.tictactoe.di.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class BoardModule {

    @ActivityScoped
    @Binds abstract fun boardPresenter(presenter: BoardPresenter): BoardContract.Presenter

    // Later add related fragments here
}
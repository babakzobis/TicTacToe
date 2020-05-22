package com.vanzoconsulting.tictactoe.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.vanzoconsulting.persistence.BoardPersistenceSource
import com.vanzoconsulting.persistence.BoardRepository
import com.vanzoconsulting.tictactoe.dispatcher.DefaultDispatcherProvider
import com.vanzoconsulting.tictactoe.dispatcher.DispatcherProvider
import com.vanzoconsulting.tictactoe.framework.SharedPrefsPersistenceSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    private const val PREFERENCES_FILE_NAME = "tictactoe_board"

    @Provides
    @Singleton
    fun boardPersistenceSource(context: Context): BoardPersistenceSource =
        SharedPrefsPersistenceSource(
            context.applicationContext.getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE)
        )

    @Provides
    @Singleton
    fun boardRepository(persistenceSource: BoardPersistenceSource) =
        BoardRepository(persistenceSource)

    @Provides
    @Singleton
    fun defaultDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}
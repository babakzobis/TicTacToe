package com.vanzoconsulting.tictactoe.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    fun main(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
}
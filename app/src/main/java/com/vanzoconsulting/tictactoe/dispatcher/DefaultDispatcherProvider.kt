package com.vanzoconsulting.tictactoe.dispatcher

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class DefaultDispatcherProvider: DispatcherProvider {

    override fun main() = Main
    override fun io() = IO
}
package com.vanzoconsulting.tictactoe.ui

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.tictactoe.dispatcher.DispatcherProvider
import com.vanzoconsulting.usecase.GetBoard
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BoardPresenter(private val view: View?, private val getBoard: GetBoard,
                     private val dispatcherProvider: DispatcherProvider) {

    fun onCreate() = GlobalScope.launch(dispatcherProvider.main()) {
        val board = withContext(dispatcherProvider.io()) {
            getBoard()
        }
        view?.renderBoard(board ?: Board())
    }

    interface View {
        fun renderBoard(board: Board)
    }
}
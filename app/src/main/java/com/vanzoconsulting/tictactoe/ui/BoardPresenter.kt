package com.vanzoconsulting.tictactoe.ui

import com.vanzoconsulting.domain.Board

class BoardPresenter(private val view: View?) {

    fun onCreate() = view?.renderBoard(Board())

    interface View {
        fun renderBoard(board: Board)
    }
}
package com.vanzoconsulting.tictactoe.ui

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player
import kotlinx.coroutines.Job

interface BoardContract {

    interface Presenter {
        var view: View?

        fun loadBoard(): Job
        fun makeMove(board: Board, index: Int)
    }

    interface View {

        fun renderBoard(board: Board)
        fun showWinner(player: Player)
        fun showDraw()
        fun reset()
    }
}
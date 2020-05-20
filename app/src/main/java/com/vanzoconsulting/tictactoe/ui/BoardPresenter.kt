package com.vanzoconsulting.tictactoe.ui

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player
import com.vanzoconsulting.tictactoe.dispatcher.DispatcherProvider
import com.vanzoconsulting.usecase.DeleteBoard
import com.vanzoconsulting.usecase.GetBoard
import com.vanzoconsulting.usecase.SaveBoard
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BoardPresenter(private val view: View?, private val getBoard: GetBoard,
                     private val saveBoard: SaveBoard, private val deleteBoard: DeleteBoard,
                     private val dispatcherProvider: DispatcherProvider) {

    fun onCreate() = GlobalScope.launch(dispatcherProvider.main()) {
        val board = withContext(dispatcherProvider.io()) {
            getBoard()
        }

        view?.reset()
        view?.renderBoard(board ?: Board())
    }

    fun makeMove(board: Board, index: Int) {
        board.mark(index).takeIf { it != board } ?.let { updatedBoard ->
            view?.renderBoard(updatedBoard)

            updatedBoard.winner?.let {
                view?.showWinner(it)
            }

            if (updatedBoard.isDraw) {
                view?.showDraw()
            }

            GlobalScope.launch(dispatcherProvider.io()) {
                if (updatedBoard.isComplete()) {
                    deleteBoard()
                } else {
                    saveBoard(updatedBoard)
                }
            }
        }
    }

    interface View {
        fun renderBoard(board: Board)
        fun showWinner(player: Player)
        fun showDraw()
        fun reset()
    }
}
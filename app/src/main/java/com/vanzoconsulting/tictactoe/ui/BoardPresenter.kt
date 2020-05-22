package com.vanzoconsulting.tictactoe.ui

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.tictactoe.di.ActivityScoped
import com.vanzoconsulting.tictactoe.dispatcher.DispatcherProvider
import com.vanzoconsulting.tictactoe.ui.BoardContract.View
import com.vanzoconsulting.usecase.DeleteBoard
import com.vanzoconsulting.usecase.GetBoard
import com.vanzoconsulting.usecase.SaveBoard
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityScoped
class BoardPresenter @Inject constructor(
    private val getBoard: GetBoard,
    private val saveBoard: SaveBoard,
    private val deleteBoard: DeleteBoard,
    private val dispatcherProvider: DispatcherProvider
): BoardContract.Presenter {

    override var view: View? = null

    override fun loadBoard() = GlobalScope.launch(dispatcherProvider.main()) {
        val board = withContext(dispatcherProvider.io()) {
            getBoard()
        }

        view?.reset()
        view?.renderBoard(board ?: Board())
    }

    override fun makeMove(board: Board, index: Int) {
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
}
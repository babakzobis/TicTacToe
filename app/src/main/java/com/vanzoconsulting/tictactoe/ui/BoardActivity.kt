package com.vanzoconsulting.tictactoe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.children
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player
import com.vanzoconsulting.domain.label
import com.vanzoconsulting.persistence.BoardRepository
import com.vanzoconsulting.tictactoe.R
import com.vanzoconsulting.tictactoe.dispatcher.DefaultDispatcherProvider
import com.vanzoconsulting.tictactoe.framework.SharedPrefsPersistenceSource
import com.vanzoconsulting.usecase.DeleteBoard
import com.vanzoconsulting.usecase.GetBoard
import com.vanzoconsulting.usecase.SaveBoard
import kotlinx.android.synthetic.main.activity_board.*

class BoardActivity: AppCompatActivity(), BoardPresenter.View {

    lateinit var presenter: BoardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_board)

        val sharedPrefsPersistenceSource = SharedPrefsPersistenceSource(
            getSharedPreferences("board", MODE_PRIVATE)
        )
        val repository = BoardRepository(sharedPrefsPersistenceSource)
        presenter = BoardPresenter(this, GetBoard(repository), SaveBoard(repository),
            DeleteBoard(repository), DefaultDispatcherProvider())
        presenter.onCreate()
    }

    override fun renderBoard(board: Board) {
        container_root.children.filterIsInstance(TextView::class.java).forEachIndexed { index, view ->
            view.text = board[index]?.label()

            if (view.text.isEmpty()) {
                view.isClickable = true
                view.setOnClickListener {
                    presenter.makeMove(board, index)
                }
            } else {
                view.isClickable = false
                view.setOnClickListener(null)
            }
        }
    }

    override fun showWinner(player: Player) {
        Snackbar.make(container_root, getString(R.string.board_message_winner, player), LENGTH_INDEFINITE)
            .setAction(R.string.board_message_action) {
                presenter.onCreate()
            }
            .show()
    }

    override fun showDraw() {
        Snackbar.make(container_root, R.string.board_message_draw, LENGTH_INDEFINITE)
            .setAction(R.string.board_message_action) {
                presenter.onCreate()
            }
            .show()
    }
}

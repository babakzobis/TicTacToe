package com.vanzoconsulting.tictactoe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.children
import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.label
import com.vanzoconsulting.persistence.BoardRepository
import com.vanzoconsulting.tictactoe.R
import com.vanzoconsulting.tictactoe.dispatcher.DefaultDispatcherProvider
import com.vanzoconsulting.tictactoe.framework.SharedPrefsPersistenceSource
import com.vanzoconsulting.usecase.GetBoard
import kotlinx.android.synthetic.main.activity_board.*

class BoardActivity: AppCompatActivity(), BoardPresenter.View {

    private lateinit var presenter: BoardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_board)

        val sharedPrefsPersistenceSource = SharedPrefsPersistenceSource(
            getSharedPreferences("board", MODE_PRIVATE)
        )
        presenter = BoardPresenter(this, GetBoard(BoardRepository(sharedPrefsPersistenceSource)),
            DefaultDispatcherProvider())
        presenter.onCreate()
    }

    override fun renderBoard(board: Board) {
        container_root.children.forEachIndexed { index, view ->
            (view as TextView).text = board[index]?.label()
        }
    }
}

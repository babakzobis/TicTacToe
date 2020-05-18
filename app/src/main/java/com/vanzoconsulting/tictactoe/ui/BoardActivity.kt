package com.vanzoconsulting.tictactoe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.tictactoe.R

class BoardActivity: AppCompatActivity(), BoardPresenter.View {

    private lateinit var presenter: BoardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_board)

        presenter = BoardPresenter(this)
        presenter.onCreate()
    }

    override fun renderBoard(board: Board) {
    }
}

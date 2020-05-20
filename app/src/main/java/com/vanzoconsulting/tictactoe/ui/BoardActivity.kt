package com.vanzoconsulting.tictactoe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.getColor
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.core.text.HtmlCompat.fromHtml
import androidx.core.view.children
import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player
import com.vanzoconsulting.domain.Player.O
import com.vanzoconsulting.domain.Player.X
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
        container_root.children.forEachIndexed { index, view ->
            (view as TextView).text = board[index]?.label()
        }
    }

    override fun showWinner(player: Player) {

    }

    override fun showDraw() {

    }

    @Suppress("UNUSED_PARAMETER")
    fun onRestartClick(v: View) {
    }

    override fun reset() {
    }
}

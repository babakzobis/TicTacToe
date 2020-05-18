package com.vanzoconsulting.tictactoe.ui

import android.widget.TextView
import androidx.core.view.children
import com.google.common.truth.Truth.assertThat
import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player.X
import com.vanzoconsulting.domain.Player.O
import kotlinx.android.synthetic.main.activity_board.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class BoardActivityTest {

    private lateinit var activity: BoardActivity

    @Before
    fun setUp() {
        activity = buildActivity(BoardActivity::class.java)
            .create()
            .resume()
            .get()
    }

    @Test
    fun onLaunchExpectEmptyBoard() {
        assertThat(activity.container_root.childCount).isEqualTo(9)

        activity.container_root.children.forEach { cellView ->
            assertThat(cellView).isInstanceOf(TextView::class.java)
            assertThat((cellView as TextView).text.toString()).isEmpty()
        }
    }

    @Test
    fun renderBoardWhenEmptyExpectNothing() {
        activity.renderBoard(Board())

        activity.container_root.children.forEach { cellView ->
            assertThat((cellView as TextView).text.toString()).isEmpty()
        }
    }

    @Test
    fun renderBoardWhenFullExpectLabels() {
        val board = Board(arrayOf(
            X, O, O,
            X, X, O,
            O, null, O
        ))

        activity.renderBoard(board)

        val moves = activity.container_root.children
            .filterIsInstance(TextView::class.java)
            .map { it.text.toString() }
            .asIterable()

        assertThat(moves).containsExactly("X", "O", "O", "X", "X", "O", "O", "", "O")
    }
}
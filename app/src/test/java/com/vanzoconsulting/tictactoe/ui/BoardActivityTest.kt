package com.vanzoconsulting.tictactoe.ui

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.core.view.children
import com.google.common.truth.Truth.assertThat
import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player.X
import com.vanzoconsulting.domain.Player.O
import com.vanzoconsulting.tictactoe.R
import kotlinx.android.synthetic.main.activity_board.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class BoardActivityTest {

    @Mock
    private lateinit var presenter: BoardPresenter

    private lateinit var activity: BoardActivity

    @Before
    fun setUp() {
        initMocks(this)

        activity = buildActivity(BoardActivity::class.java)
            .create()
            .resume()
            .get()
            .also {
                it.presenter = presenter
            }
    }

    @Test
    fun onLaunchExpectEmptyBoardWithNoMessageNorControlButton() {
        assertThat(activity.board_grid.childCount).isEqualTo(9)

        activity.board_grid.children.forEach { childView ->
            assertThat(childView).isInstanceOf(TextView::class.java)
            assertThat((childView as TextView).text.toString()).isEmpty()
        }

        assertThat(activity.fab_restart.visibility).isEqualTo(GONE)
        assertThat(activity.tv_message.visibility).isEqualTo(GONE)
    }

    @Test
    fun renderBoardWhenEmptyExpectEmptyClickableLabels() {
        val board = Board()
        activity.renderBoard(board)

        activity.board_grid.labels().forEachIndexed { index, label ->
            assertThat(label.text.toString()).isEmpty()
            assertThat(label.hasOnClickListeners()).isTrue()

            label.callOnClick()
            verify(presenter).makeMove(board, index)
        }
    }

    @Test
    fun renderBoardWithFullCapacityExpectLockedNonEmptyLabels() {
        val board = Board(arrayOf(
            X, O, O,
            X, X, O,
            O, X, X
        ))

        activity.renderBoard(board)

        val labels = activity.board_grid.labels()
        val colorX = activity.getColor(R.color.colorPlayer)
        val colorO = activity.getColor(R.color.colorPlayer2)

        assertThat(labels.map { it.text.toString() } .asIterable())
            .containsExactly("X", "O", "O", "X", "X", "O", "O", "X", "X")

        assertThat(labels.map { it.textColors.defaultColor } .asIterable())
            .containsExactly(colorX, colorO, colorO, colorX, colorX, colorO, colorO, colorX, colorX)

        labels.forEachIndexed { index, label ->
            assertThat(label.hasOnClickListeners()).isFalse()

            label.callOnClick()
            verify(presenter, never()).makeMove(board, index)
        }
    }

    @Test
    fun showWinnerWithXExpectResultMessageAndRestartButtonDisplay() {
        activity.showWinner(X)

        assertThat(activity.fab_restart.visibility).isEqualTo(VISIBLE)
        assertThat(activity.tv_message.visibility).isEqualTo(VISIBLE)
        assertThat(activity.tv_message.text.toString()).isEqualTo("X\nWINNER!")
    }

    @Test
    fun showWinnerWithOExpectResultMessageAndRestartButtonDisplay() {
        activity.showWinner(O)

        assertThat(activity.fab_restart.visibility).isEqualTo(VISIBLE)
        assertThat(activity.tv_message.visibility).isEqualTo(VISIBLE)
        assertThat(activity.tv_message.text.toString()).isEqualTo("O\nWINNER!")
    }

    @Test
    fun showDrawExpectResultMessageAndRestartButtonDisplay() {
        activity.showDraw()

        assertThat(activity.fab_restart.visibility).isEqualTo(VISIBLE)
        assertThat(activity.tv_message.visibility).isEqualTo(VISIBLE)
        assertThat(activity.tv_message.text.toString()).isEqualTo("XO\nDRAW!")
    }

    @Test
    fun onRestartClickExpectSubsequentCallToPresenter() {
        activity.fab_restart.performClick()

        verify(presenter).onCreate()
    }

    fun resetExpectEmptyBoardAndResultMessageAndRestartButtonGone() {
        activity.reset()

        activity.board_grid.labels().forEach {
            assertThat(it.text.toString()).isEmpty()
            assertThat(it.hasOnClickListeners()).isTrue()
        }

        assertThat(activity.fab_restart.visibility).isEqualTo(GONE)
        assertThat(activity.tv_message.visibility).isEqualTo(GONE)
    }

    private fun BoardLayout.labels() = children.filterIsInstance(TextView::class.java)
}

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
    fun onLaunchExpectEmptyBoard() {
        assertThat(activity.container_root.childCount).isEqualTo(9)

        activity.container_root.children.forEach { cellView ->
            assertThat(cellView).isInstanceOf(TextView::class.java)
            assertThat((cellView as TextView).text.toString()).isEmpty()
        }
    }

    @Test
    fun renderBoardWhenEmptyExpectEmptyClickableLabels() {
        val board = Board()
        activity.renderBoard(board)

        activity.container_root.children.filterIsInstance(TextView::class.java).forEachIndexed{ index, label ->
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

        val labels = activity.container_root.children.filterIsInstance(TextView::class.java)

        assertThat(labels.map { it.text.toString() } .asIterable())
            .containsExactly("X", "O", "O", "X", "X", "O", "O", "X", "X")

        labels.forEachIndexed { index, label ->
            assertThat(label.hasOnClickListeners()).isFalse()

            label.callOnClick()
            verify(presenter, never()).makeMove(board, index)
        }
    }
}
package com.vanzoconsulting.tictactoe.ui

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player.X
import com.vanzoconsulting.domain.Player.O
import com.vanzoconsulting.usecase.DeleteBoard
import com.vanzoconsulting.usecase.GetBoard
import com.vanzoconsulting.usecase.SaveBoard
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.Spy

@ExperimentalCoroutinesApi
internal class BoardPresenterTest {

    @get:Rule
    var testRule = CoroutineTestRule()

    @Spy
    private lateinit var view: BoardPresenter.View

    @Mock
    private lateinit var getBoard: GetBoard

    @Mock
    private lateinit var saveBoard: SaveBoard

    @Mock
    private lateinit var deleteBoard: DeleteBoard

    private lateinit var presenter: BoardPresenter

    @Before
    fun setUp() {
        initMocks(this)
        presenter = BoardPresenter(view, getBoard, saveBoard, deleteBoard,
            testRule.testDispatcherProvider)
    }

    @Test
    fun onCreateExpectViewResetAndRendering() = testRule.testDispatcher.runBlockingTest {
        val board = Board(arrayOf(
            X, O, X,
            O, X, O,
            X, O, X
        ))

        `when`(getBoard()).thenReturn(board)

        presenter.onCreate()

        verify(view).reset()
        verify(view).renderBoard(board)
    }

    @Test
    fun makeMoveWithIncompleteBoardExpectUpdateAndChangeRendering() =
        testRule.testDispatcher.runBlockingTest {
            val board = Board(arrayOf(
                X, O, X,
                null, O, null,
                null, null, null
            ))
            val updatedBoard = board.mark(7)

            presenter.makeMove(board, 7)

            verify(view).renderBoard(updatedBoard)
            verify(saveBoard).invoke(updatedBoard)
        }

    @Test
    fun makeMoveCompletingBoardWithWinnerExpectDeleteAndWinnerRendering() =
        testRule.testDispatcher.runBlockingTest {
            val board = Board(arrayOf(
                X, O, X,
                null, O, null,
                X, null, null
            ))
            val updatedBoard = board.mark(7)

            presenter.makeMove(board, 7)

            verify(view).renderBoard(updatedBoard)
            verify(view).showWinner(O)
            verify(deleteBoard).invoke()
        }

    @Test
    fun makeMoveCompletingBoardWithDrawExpectDeleteAndDrawRendering() =
        testRule.testDispatcher.runBlockingTest {
            val board = Board(arrayOf(
                X, O, X,
                X, O, O,
                O, X, null
            ))
            val updatedBoard = board.mark(8)

            presenter.makeMove(board, 8)

            verify(view).renderBoard(updatedBoard)
            verify(view).showDraw()
            verify(deleteBoard).invoke()
        }

    @Test
    fun makeMoveOnMarkedIndexInBoardExpectNothing()  =
        testRule.testDispatcher.runBlockingTest {
            val board = Board(arrayOf(
                X, null, null,
                null, null, null,
                null, null, null
            ))

            presenter.makeMove(board, 0)

            verify(view, never()).renderBoard(any())
            verify(view, never()).showWinner(any())
            verify(view, never()).showDraw()

            verify(deleteBoard, never()).invoke()
            verify(saveBoard, never()).invoke(board)
        }

    private fun <T> any(): T = Mockito.any<T>()
}
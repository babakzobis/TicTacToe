package com.vanzoconsulting.tictactoe.ui

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player.X
import com.vanzoconsulting.domain.Player.O
import com.vanzoconsulting.usecase.GetBoard
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
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

    private lateinit var presenter: BoardPresenter

    @Before
    fun setUp() {
        initMocks(this)
        presenter = BoardPresenter(view, getBoard, testRule.testDispatcherProvider)
    }

    @Test
    fun onCreate() = testRule.testDispatcher.runBlockingTest {
        val board = Board(arrayOf(
            X, O, X,
            O, X, O,
            X, O, X
        ))

        `when`(getBoard()).thenReturn(board)

        presenter.onCreate()

        verify(view).renderBoard(board)
    }
}
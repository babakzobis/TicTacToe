package com.vanzoconsulting.tictactoe.ui

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player.X
import com.vanzoconsulting.domain.Player.O
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.Spy

@ExperimentalCoroutinesApi
internal class BoardPresenterTest {

    @Spy
    private lateinit var view: BoardPresenter.View

    @InjectMocks
    private lateinit var presenter: BoardPresenter

    @Before
    fun setUp() {
        initMocks(this)
    }

    @Test
    fun onCreate() {
        val board = Board(arrayOf(
            X, O, X,
            O, X, O,
            X, O, X
        ))

        presenter.onCreate()

        verify(view).renderBoard(board)
    }
}
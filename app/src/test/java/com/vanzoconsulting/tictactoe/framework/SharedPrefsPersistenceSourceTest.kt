package com.vanzoconsulting.tictactoe.framework

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player.O
import com.vanzoconsulting.domain.Player.X
import org.junit.Assert.assertEquals
import org.junit.Test

internal class SharedPrefsPersistenceSourceTest {

    private val persistenceSource = SharedPrefsPersistenceSource()

    @Test
    fun saveBoard() {
        val board = Board(
            arrayOf(
                X, O, X,
                O, X, null,
                null, null, null
            )
        )

        val saved = persistenceSource.saveBoard(board)

        assertEquals(true, saved)
    }

    @Test
    fun loadBoard() {
        val expectedBoard = Board(
            arrayOf(
                X, O, X,
                O, X, null,
                null, null, null
            )
        )
        val actualBoard = persistenceSource.loadBoard()

        assertEquals(expectedBoard, actualBoard)
    }
}
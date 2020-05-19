package com.vanzoconsulting.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import com.vanzoconsulting.domain.Player.X
import com.vanzoconsulting.domain.Player.O

internal class BoardTest {

    @Test
    fun getWhenNullExpectNull() {
        assertEquals(null, Board()[1, 2])
    }

    @Test
    fun getFirstIndexWhenInitializedWithXExpectX() {
        val content: Array<Player?> = Array(9) { null }
        content[4] = X

        assertEquals(X, Board(content)[1, 1])
    }

    @Test
    fun getSecondIndexWhenInitializedWithXOExpectO() {
        val content: Array<Player?> = Array(9) { null }
        content[0] = X
        content[1] = O

        val board = Board(content)

        assertEquals(board[0, 0], X)
        assertEquals(board[1, 0], O)
    }

    @Test
    fun toStringExpectStringRepresentationOfContentArray() {
        val content = arrayOf(
            X, O, X,
            O, X, null,
            null, null, null
        )
        val contentString = "[X, O, X, O, X, null, null, null, null]"

        assertEquals(contentString, Board(content).toString())
    }

    @Test
    fun getWinnerWhenBoardIsEmptyExpectNull() {
        assertNull(Board().winner)
    }

    @Test
    fun getWinnerWhenBoardInProgressExpectNull() {
        val board = Board(arrayOf(
            X, null, null,
            O, X, null,
            null, null, O
        ))
        assertNull(board.winner)
    }

    @Test
    fun getWinnerWhenXIsAlignedExpectX() {
        val board = Board(arrayOf(
            X, X, X,
            O, X, null,
            O, null, O
        ))

        assertEquals(X, board.winner)
    }

    @Test
    fun getWinnerWhenOIsAlignedExpectO() {
        val board = Board(arrayOf(
            O, O, O,
            X, O, null,
            X, null, X
        ))

        assertEquals(O, board.winner)
    }

    @Test
    fun isDrawWhenBoardIsEmptyExpectFalse() {
        assertFalse(Board().isDraw)
    }

    @Test
    fun isDrawWhenBoardIsCompletedWithWinnerExpectFalse() {
        val board = Board(arrayOf(
            X, X, X,
            O, X, null,
            O, null, O
        ))

        assertFalse(board.isDraw)
    }

    @Test
    fun isDrawWhenBoardIsCompletedWithoutWinnerExpectTrue() {
        val board = Board(arrayOf(
            X, O, X,
            O, X, O,
            X, O, X
        ))

        assertTrue(board.isDraw)
    }

    @Test
    fun markWhenBoardIsEmptyExpectCopyWithX() {
        assertEquals(X, Board().mark(0)[0])
    }

    @Test
    fun markWhenBoardHasStartedExpectCopyWithO() {
        assertEquals(O, Board().mark(0).mark(1))
    }

    @Test
    fun markWhenWinnerIsSetExpectCopyWithoutChange() {
        val board = Board(arrayOf(
            X, X, X,
            O, O, null,
            null, null, null
        ))

        assertEquals(board, board.mark(8))
    }

    @Test
    fun markWhenBoardIsFullExpectCopyWithoutChange() {
        val board = Board(arrayOf(
            X, O, X,
            O, X, O,
            X, O, X
        ))

        assertEquals(board, board.mark(8))
    }

    @Test
    fun isCompletedWhenBoardIsFullExpectTrue() {
        val board = Board(arrayOf(
            X, O, X,
            O, X, O,
            X, O, X
        ))

        assertTrue(board.isCompleted())
    }

    @Test
    fun isCompletedWhenBoardHasAWinnerExpectTrue() {
        val board = Board(arrayOf(
            X, X, X,
            O, O, null,
            null, null, null
        ))

        assertTrue(board.isCompleted())
    }

    @Test
    fun isCompletedWhenBoardHasCapacityWithNoWinnerExpectFalse() {
        val board = Board(arrayOf(
            X, null, null,
            O, X, null,
            null, null, O
        ))

        assertFalse(board.isCompleted())
    }
}
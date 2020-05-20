package com.vanzoconsulting.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import com.vanzoconsulting.domain.Player.X
import com.vanzoconsulting.domain.Player.O

internal class BoardTest {

    private val boardCompleteWithDraw = Board(arrayOf(
        X, X, O,
        O, O, X,
        X, O, X
    ))
    private val boardCompleteWithXWinner = Board(arrayOf(
        X, X, X,
        O, X, null,
        O, null, O
    ))
    private val boardCompleteWithOWinner = Board(arrayOf(
        X, X, O,
        X, O, null,
        O, null, null
    ))
    private val boardIncomplete = Board(arrayOf(
        X, null, null,
        O, X, null,
        null, null, O
    ))

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
        assertNull(boardIncomplete.winner)
    }

    @Test
    fun getWinnerWhenXIsAlignedExpectX() {
        assertEquals(X, boardCompleteWithXWinner.winner)
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
    fun isDrawWhenBoardIsCompleteWithWinnerExpectFalse() {
        assertFalse(boardCompleteWithXWinner.isDraw)
    }

    @Test
    fun isDrawWhenBoardIsCompleteWithoutWinnerExpectTrue() {
        assertTrue(boardCompleteWithDraw.isDraw)
    }

    @Test
    fun markWhenBoardIsEmptyExpectCopyMovedByX() {
        val expectedBoard = Board(arrayOf(
            X, null, null,
            null, null, null,
            null, null, null
        ))
        val actualBoard = Board().mark(0)

        assertEquals(expectedBoard, actualBoard)
    }

    @Test
    fun markWhenBoardHasStartedExpectCopyMovedByO() {
        val expectedBoard = Board(arrayOf(
            X, O, null,
            null, null, null,
            null, null, null
        ))
        val actualBoard = Board().mark(0).mark(1)

        assertEquals(expectedBoard, actualBoard)
    }

    @Test
    fun markWhenBoardIsCompleteWithWinnerExpectExactCopy() {
        val expectedBoard = boardCompleteWithOWinner
        val actualBoard = expectedBoard.mark(8)

        assertEquals(expectedBoard, actualBoard)
    }
    @Test
    fun markWhenBoardIsCompleteWithDrawExpectExactCopy() {
        val expectedBoard = boardCompleteWithDraw
        val actualBoard = expectedBoard.mark(8)

        assertEquals(expectedBoard, actualBoard)
    }

    @Test
    fun markWhenBoardIsAlreadyMarkedAtThatPositionExpectExactCopy() {
        val initialBoard = boardIncomplete
        val actualBoard = boardIncomplete.mark(0)

        assertEquals(initialBoard, actualBoard)
    }

    @Test
    fun isCompleteWhenBoardIsFullExpectTrue() {
        val board = Board(arrayOf(
            X, O, X,
            O, X, O,
            X, O, X
        ))

        assertTrue(board.isComplete())
    }

    @Test
    fun isCompleteWhenBoardHasAWinnerExpectTrue() {
        assertTrue(boardCompleteWithOWinner.isComplete())
    }

    @Test
    fun isCompleteWhenBoardHasCapacityWithoutWinnerExpectFalse() {
        assertFalse(boardIncomplete.isComplete())
    }
}
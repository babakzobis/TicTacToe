package com.vanzoconsulting.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class BoardTest {

    @Test
    fun getWhenNullExpectNull() {
        assertEquals(null, Board()[1, 2])
    }

    @Test
    fun getFirstIndexWhenInitializedWithXExpectX() {
        val content: Array<Player?> = Array(9) { null }
        content[4] = Player.X

        assertEquals(Player.X, Board(content)[1, 1])
    }

    @Test
    fun getSecondIndexWhenInitializedWithXOExpectO() {
        val content: Array<Player?> = Array(9) { null }
        content[0] = Player.X
        content[1] = Player.O

        val board = Board(content)

        assertEquals(board[0, 0], Player.X)
        assertEquals(board[1, 0], Player.O)
    }

    @Test
    fun toStringExpectStringRepresentationOfContentArray() {
        val content = arrayOf(
            Player.X, Player.O, Player.X,
            Player.O, Player.X, null,
            null, null, null
        )
        val contentString = "[X, O, X, O, X, null, null, null, null]"

        assertEquals(contentString, Board(content).toString())
    }
}
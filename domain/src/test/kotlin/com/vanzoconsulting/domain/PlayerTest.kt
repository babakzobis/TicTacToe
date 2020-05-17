package com.vanzoconsulting.domain

import com.vanzoconsulting.domain.Player.Companion.valueOf
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PlayerTest {
    @Test
    fun labelWhenPlayerIsXExpectX() {
        assertEquals(Player.X.label(), "X")
    }

    @Test
    fun labelWhenPlayerIsOExpectO() {
        assertEquals(Player.O.label(), "O")
    }

    @Test
    fun labelWhenPlayerIsNullExpectEmpty() {
        val player : Player? = null
        assertEquals(player.label(), "")
    }

    @Test
    fun valueOfPlayerWhenLabelIsXExpectX() {
        assertEquals(Player.X, valueOf("X"))
    }

    @Test
    fun valueOfPlayerWhenLabelIsOExpectO() {
        assertEquals(Player.O, valueOf("O"))
    }

    @Test
    fun valueOfPlayerWhenLabelIsEmptyExpectNull() {
        assertNull(valueOf(""))
    }

    @Test
    fun valueOfPlayerWhenLabelIsNullExpectNull() {
        assertNull(valueOf("null"))
    }
}
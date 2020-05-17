package com.vanzoconsulting.usecase

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player.O
import com.vanzoconsulting.domain.Player.X
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class GetBoardTest {

    lateinit var usecase: GetBoard

    @Test
    fun invokeExpectSubsequentCallToRepository() {
        val board = Board(
            arrayOf(
                X, X, X,
                O, O, null,
                null, null, null
            )
        )

        assertEquals(board, usecase())
    }
}
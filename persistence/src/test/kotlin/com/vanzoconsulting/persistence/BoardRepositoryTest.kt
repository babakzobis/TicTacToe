package com.vanzoconsulting.persistence

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player.O
import com.vanzoconsulting.domain.Player.X
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class BoardRepositoryTest {

    private val boardRepository = BoardRepository()

    private val board = Board(
        arrayOf(
            X, O, X,
            O, X, O,
            X, O, X
        )
    )

    @Test
    fun loadBoardExpectSubsequentCallToRepositorySource() {
        assertEquals(board, boardRepository.loadBoard())
    }

    @Test
    fun saveBoardExpectSubsequentCallToRepositorySource() {
        assertEquals(true, boardRepository.saveBoard(board))
    }
}
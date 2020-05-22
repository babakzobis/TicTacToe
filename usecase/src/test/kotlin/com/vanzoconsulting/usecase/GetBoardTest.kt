package com.vanzoconsulting.usecase

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player.X
import com.vanzoconsulting.domain.Player.O
import com.vanzoconsulting.persistence.BoardRepository
import com.vanzoconsulting.persistence.BoardPersistenceSource
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class GetBoardTest {

    @Spy
    private val repository = BoardRepository(mock(BoardPersistenceSource::class.java))

    @InjectMocks
    private lateinit var usecase: GetBoard

    @BeforeEach
    fun setUp() {
        initMocks(this)
    }

    @Test
    fun invokeExpectSubsequentCallToRepository() {
        val board = Board(arrayOf(
            X, X, X,
            O, O, null,
            null, null, null
        ))

        `when`(repository.loadBoard()).thenReturn(board)

        assertEquals(board, usecase())
    }
}
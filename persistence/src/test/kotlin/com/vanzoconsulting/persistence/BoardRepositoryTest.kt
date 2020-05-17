package com.vanzoconsulting.persistence

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player.X
import com.vanzoconsulting.domain.Player.O
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class BoardRepositoryTest {

    @Spy
    lateinit var boardPersistenceSource: BoardPersistenceSource

    @InjectMocks
    lateinit var boardRepository: BoardRepository

    private val board = Board(arrayOf(
        X, O, X,
        O, X, O,
        X, O, X
    ))

    @BeforeEach
    fun setUp() {
        initMocks(this)
    }

    @Test
    fun loadBoardExpectSubsequentCallToRepositorySource() {
        `when`(boardPersistenceSource.loadBoard()).thenReturn(board)

        assertEquals(board, boardRepository.loadBoard())
    }

    @Test
    fun saveBoardExpectSubsequentCallToRepositorySource() {
        `when`(boardPersistenceSource.saveBoard(board)).thenReturn(true)

        assertEquals(true, boardRepository.saveBoard(board))
    }
}
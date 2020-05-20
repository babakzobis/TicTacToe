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
    lateinit var persistenceSource: BoardPersistenceSource

    @InjectMocks
    lateinit var repository: BoardRepository

    private val boardFromSource = Board(arrayOf(
        X, O, X,
        O, X, O,
        X, O, X
    ))

    @BeforeEach
    fun setUp() {
        initMocks(this)
    }

    @Test
    fun loadBoardExpectSubsequentCallToRepositorySourceAndItsResult() {
        `when`(persistenceSource.loadBoard()).thenReturn(boardFromSource)

        assertEquals(boardFromSource, repository.loadBoard())
    }

    @Test
    fun saveBoardExpectSubsequentCallToRepositorySourceAndItsResult() {
        `when`(persistenceSource.saveBoard(boardFromSource)).thenReturn(SOURCE_POSITIVE_RESULT)

        assertEquals(SOURCE_POSITIVE_RESULT, repository.saveBoard(boardFromSource))
    }

    @Test
    fun deleteBoardExpectSubsequentCallToRepositorySourceAndItsResult() {
        `when`(persistenceSource.deleteBoard()).thenReturn(SOURCE_POSITIVE_RESULT)

        assertEquals(SOURCE_POSITIVE_RESULT, repository.deleteBoard())
    }

    companion object {
        const val SOURCE_POSITIVE_RESULT = true
    }
}
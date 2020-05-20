package com.vanzoconsulting.usecase

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player.X
import com.vanzoconsulting.domain.Player.O
import com.vanzoconsulting.persistence.BoardPersistenceSource
import com.vanzoconsulting.persistence.BoardRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class SaveBoardTest {

    @Spy
    private val repository = BoardRepository(mock(BoardPersistenceSource::class.java))

    @InjectMocks
    private lateinit var usecase: SaveBoard

    @BeforeEach
    fun setUp() {
        initMocks(this)
    }

    @Test
    operator fun invoke() {
        val board = Board(arrayOf(
            X, X, X,
            O, O, null,
            null, null, null
        ))

        assertTrue(usecase(board))

        verify(repository).saveBoard(board)
    }
}
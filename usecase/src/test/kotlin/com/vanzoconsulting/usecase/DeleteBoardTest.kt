package com.vanzoconsulting.usecase

import com.vanzoconsulting.persistence.BoardPersistenceSource
import com.vanzoconsulting.persistence.BoardRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class DeleteBoardTest {

    @Spy
    private val repository = BoardRepository(mock(BoardPersistenceSource::class.java))

    @InjectMocks
    private lateinit var usecase: DeleteBoard

    @BeforeEach
    fun setUp() {
        initMocks(this)
    }

    @Test
    fun invokeExpectSubsequentCallToRepository() {
        `when`(repository.deleteBoard()).thenReturn(true)

        assertTrue(usecase())
    }
}
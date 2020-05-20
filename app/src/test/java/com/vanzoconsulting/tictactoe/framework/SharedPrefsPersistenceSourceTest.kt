package com.vanzoconsulting.tictactoe.framework

import android.content.SharedPreferences
import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player.O
import com.vanzoconsulting.domain.Player.X
import com.vanzoconsulting.tictactoe.framework.SharedPrefsPersistenceSource.Companion.PREF_KEY_BOARD
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class SharedPrefsPersistenceSourceTest {

    @Mock
    private lateinit var prefs: SharedPreferences

    @InjectMocks
    private lateinit var persistenceSource: SharedPrefsPersistenceSource

    @Before
    fun setUp() {
        initMocks(this)
    }

    @Test
    fun loadBoard() {
        `when`(
            prefs.getString(
                PREF_KEY_BOARD,
                null
            )
        ).thenReturn("[X, O, X, O, X, null, null, null, null]")

        val expectedBoard = Board(
            arrayOf(
                X, O, X,
                O, X, null,
                null, null, null
            )
        )
        val actualBoard = persistenceSource.loadBoard()

        assertEquals(expectedBoard, actualBoard)
    }

    @Test
    fun saveBoard() {
        val board = Board(
            arrayOf(
                X, O, X,
                O, X, null,
                null, null, null
            )
        )

        val editor = mock(SharedPreferences.Editor::class.java)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)
        `when`(editor.commit()).thenReturn(true)

        `when`(prefs.edit()).thenReturn(editor)

        val saved = persistenceSource.saveBoard(board)

        assertTrue(saved)
        verify(editor).putString(PREF_KEY_BOARD, board.toString())
        verify(editor).commit()
    }

    @Test
    fun deleteBoard() {
        val editor = mock(SharedPreferences.Editor::class.java)
        `when`(editor.remove(anyString())).thenReturn(editor)
        `when`(editor.commit()).thenReturn(true)

        `when`(prefs.edit()).thenReturn(editor)

        val deleted = persistenceSource.deleteBoard()

        assertTrue(deleted)
        verify(editor).remove(PREF_KEY_BOARD)
        verify(editor).commit()
    }
}
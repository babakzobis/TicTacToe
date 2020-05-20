package com.vanzoconsulting.tictactoe.framework

import android.content.SharedPreferences
import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.domain.Player.Companion.valueOf
import com.vanzoconsulting.persistence.BoardPersistenceSource

class SharedPrefsPersistenceSource(private val prefs: SharedPreferences): BoardPersistenceSource {

    override fun loadBoard(): Board? =
        prefs.getString(PREF_KEY_BOARD, null)
            ?.replace("[", "")  //remove the right bracket
            ?.replace("]", "")  //remove the left bracket
            ?.split(", ")              //separate by comma
            ?.map { it -> valueOf(it) }
            ?.toTypedArray()
            ?.let {
                Board(it)
            }

    override fun saveBoard(board: Board) =
        prefs.edit().putString(PREF_KEY_BOARD, board.toString()).commit()

    override fun deleteBoard() = prefs.edit().remove(PREF_KEY_BOARD).commit()

    companion object {
        const val PREF_KEY_BOARD = "board"
    }
}
package com.vanzoconsulting.tictactoe.framework

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.persistence.BoardPersistenceSource

class SharedPrefsPersistenceSource : BoardPersistenceSource {

    override fun loadBoard(): Board? = null
    override fun saveBoard(board: Board) = false
}
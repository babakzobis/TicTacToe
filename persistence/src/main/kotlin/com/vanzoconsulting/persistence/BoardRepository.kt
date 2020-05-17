package com.vanzoconsulting.persistence

import com.vanzoconsulting.domain.Board

class BoardRepository(private val source: BoardPersistenceSource) {

    fun loadBoard() = source.loadBoard()
    fun saveBoard(board: Board) = source.saveBoard(board)
}

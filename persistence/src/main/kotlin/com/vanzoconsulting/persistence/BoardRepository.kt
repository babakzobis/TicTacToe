package com.vanzoconsulting.persistence

import com.vanzoconsulting.domain.Board
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BoardRepository @Inject constructor(private val source: BoardPersistenceSource) {

    fun loadBoard() = source.loadBoard()
    fun saveBoard(board: Board) = source.saveBoard(board)
    fun deleteBoard() = source.deleteBoard()
}

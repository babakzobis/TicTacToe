package com.vanzoconsulting.persistence

import com.vanzoconsulting.domain.Board

interface BoardPersistenceSource {

    fun loadBoard(): Board?
    fun saveBoard(board: Board): Boolean
    fun deleteBoard(): Boolean
}
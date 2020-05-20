package com.vanzoconsulting.usecase

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.persistence.BoardRepository

class SaveBoard(private val repository: BoardRepository) {
    operator fun invoke(board: Board) = false
}

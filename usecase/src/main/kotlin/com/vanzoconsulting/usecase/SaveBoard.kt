package com.vanzoconsulting.usecase

import com.vanzoconsulting.domain.Board
import com.vanzoconsulting.persistence.BoardRepository
import javax.inject.Inject

class SaveBoard @Inject constructor(private val repository: BoardRepository) {
    operator fun invoke(board: Board) = repository.saveBoard(board)
}

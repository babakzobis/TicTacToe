package com.vanzoconsulting.usecase

import com.vanzoconsulting.persistence.BoardRepository

class GetBoard(private val repository: BoardRepository) {
    operator fun invoke() = repository.loadBoard()
}

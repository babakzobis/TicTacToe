package com.vanzoconsulting.usecase

import com.vanzoconsulting.persistence.BoardRepository

class DeleteBoard(private val repository: BoardRepository) {
    operator fun invoke() = repository.deleteBoard()
}
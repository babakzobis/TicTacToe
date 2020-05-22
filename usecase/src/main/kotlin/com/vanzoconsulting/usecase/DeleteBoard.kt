package com.vanzoconsulting.usecase

import com.vanzoconsulting.persistence.BoardRepository
import javax.inject.Inject

class DeleteBoard @Inject constructor(private val repository: BoardRepository) {
    operator fun invoke() = repository.deleteBoard()
}
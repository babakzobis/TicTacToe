package com.vanzoconsulting.usecase

import com.vanzoconsulting.persistence.BoardRepository
import javax.inject.Inject

class GetBoard @Inject constructor(private val repository: BoardRepository) {
    operator fun invoke() = repository.loadBoard()
}

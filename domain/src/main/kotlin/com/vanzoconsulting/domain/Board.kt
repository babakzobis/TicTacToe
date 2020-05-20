package com.vanzoconsulting.domain

import com.vanzoconsulting.domain.Player.X
import com.vanzoconsulting.domain.Player.O

/**
 * Class representing the game's board organized in 9 indexes starting from the upper left corner.
 * Each index contains a move made by a player symbolized by "X" or "O".
 */
data class Board(private val squares: Array<Player?> = Array(9) { null }) {

    private val winningLineIndexes = arrayOf(
        Triple(0, 1, 2),
        Triple(3, 4, 5),
        Triple(6, 7, 8),
        Triple(0, 3, 6),
        Triple(1, 4, 7),
        Triple(2, 5, 8),
        Triple(0, 4, 8),
        Triple(2, 4, 6)
    )

    val winner: Player? = findWinner()

    val isDraw = when(winner) {
        null -> squares.filterNotNull().size == 9
        else -> false
    }

    private val nextPlayer = when {
        isComplete() -> null
        squares.count(X) == squares.count(O) -> X
        else -> O
    }

    private fun findWinner(): Player? {
        if (squares.filterNotNull().size >= 5) {
            for ((a, b, c) in winningLineIndexes) {
                if (squares[a] != null && squares[a] == squares[b] && squares[b] == squares[c]) {
                    return squares[a]
                }
            }
        }

        return null
    }

    private fun Array<Player?>.count(p: Player) = count { it?.ordinal == p.ordinal }

    private fun getIndex(x: Int, y: Int) = x + (y * 3)

    operator fun get(x: Int, y: Int): Player? = squares[getIndex(x, y)]
    operator fun get(index: Int) = squares[index]

    fun mark(index: Int) = Board(squares.copyOf().also {
        if (get(index) == null) {
            nextPlayer?.let { player ->
                it[index] = player
            }
        }
    })

    fun isComplete() = winner != null || isDraw

    // generated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Board

        if (!squares.contentEquals(other.squares)) return false

        return true
    }

    // generated
    override fun hashCode(): Int {
        return squares.contentHashCode()
    }

    override fun toString(): String {
        return squares.contentToString()
    }
}

package com.vanzoconsulting.domain

/**
 * Class representing the game's board organized in 9 indexes starting from the upper left corner.
 * Each index contains a move made by a player symbolized by "X" or "O".
 */
data class Board(private val squares: Array<Player?> = Array(9) { null }) {

    private fun getIndex(x: Int, y: Int) = x + (y * 3)

    operator fun get(x: Int, y: Int): Player? = squares[getIndex(x, y)]
    operator fun get(index: Int) = squares[index]

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
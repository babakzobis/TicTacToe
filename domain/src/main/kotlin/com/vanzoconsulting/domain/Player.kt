package com.vanzoconsulting.domain

enum class Player {
    X,
    O;

    override fun toString() = label()

    companion object {
        fun valueOf(name: String) = when(name) {
            X.name -> X
            O.name -> O
            else -> null
        }
    }
}

fun Player?.label() = when(this) {
    Player.X -> Player.X.name
    Player.O -> Player.O.name
    else -> ""
}
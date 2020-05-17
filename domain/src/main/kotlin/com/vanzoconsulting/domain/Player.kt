package com.vanzoconsulting.domain

enum class Player {
    X,
    O;

    companion object {
        fun valueOf(name: String) : Player? = null
    }
}

fun Player?.label() = null
package net.nolit.japanesechess.domain.entity

import net.nolit.japanesechess.domain.entity.piece.Piece

interface BoardInitializable {
    fun initialize(): MutableList<Piece>
}
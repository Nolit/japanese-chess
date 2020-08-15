package net.nolit.japanesechess.domain.entity

import net.nolit.japanesechess.domain.entity.piece.Piece
import net.nolit.japanesechess.domain.value.Position

class PieceInHand(
        val name: String,
        private val createPiece: (position: Position) -> Piece
) {
    fun drop(position: Position): Piece {
        return createPiece(position)
    }
}
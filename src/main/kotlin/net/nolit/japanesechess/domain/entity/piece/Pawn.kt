package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.entity.PieceInHand
import net.nolit.japanesechess.domain.value.Position

class Pawn(override val position: Position, override val isBlack: Boolean): Piece(position, isBlack) {
    override fun listPositionAvailableToMove(): List<Position> {
        return when(isBlack) {
            true -> if(position.canMoveToFront(1)) listOf(position.toFront(1)) else listOf()
            false -> if(position.canMoveToBack(1)) listOf(position.toBack(1)) else listOf()
        }
    }

    override fun setPosition(position: Position): Piece {
        return Pawn(position, isBlack)
    }

    override fun mustPromote(toPosition: Position): Boolean {
        return when(isBlack) {
            true -> toPosition.y==0
            false -> toPosition.y==8
        }
    }

    override fun promote(): PromotedPawn {
        return PromotedPawn(position, isBlack)
    }

    override fun toPieceInHand(): PieceInHand {
        return PieceInHand (getName()) { position -> Pawn(position, !isBlack) }
    }

    override fun getName(): String {
        return "Pawn"
    }
}
package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.entity.PieceInHand
import net.nolit.japanesechess.domain.value.Position

class Pawn(override val position: Position, override val isBlack: Boolean): Piece(position, isBlack) {
    override fun listPositionAvailableToMove(): List<Position> {
        //歩は動けない状況になることはないので盤外チェックは不要
        return when(isBlack) {
            true -> listOf(position.toFront(1))
            false -> listOf(position.toBack(1))
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
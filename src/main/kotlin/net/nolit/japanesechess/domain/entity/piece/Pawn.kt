package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.entity.PieceInHand
import net.nolit.japanesechess.domain.value.Position

class Pawn(private val position: Position, private val isBlack: Boolean): Piece(position, isBlack) {
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
            true -> toPosition.y==8
            false -> toPosition.y==0
        }
    }

    override fun promote(): Piece {
        return PromotedPawn(position, isBlack)
    }

    override fun toPieceInHand(): PieceInHand {
        return PieceInHand { position -> Pawn(position, !isBlack) }
    }
}
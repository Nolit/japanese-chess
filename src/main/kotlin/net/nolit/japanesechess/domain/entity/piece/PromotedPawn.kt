package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.entity.PieceInHand
import net.nolit.japanesechess.domain.value.Position

class PromotedPawn(override val position: Position, override val isBlack: Boolean): Piece(position, isBlack) {
    override fun listPositionAvailableToMove(): List<Position> {
        return when(isBlack) {
            true -> listOf(
                    if(position.canMoveToFront(1)) position.toFront(1) else null,
                    if(position.toFront(1).canMoveToRight(1)) position.toFront(1).toRight(1) else null,
                    if(position.toFront(1).canMoveToLeft(1)) position.toFront(1).toLeft(1) else null,
                    if(position.canMoveToRight(1)) position.toRight(1) else null,
                    if(position.canMoveToLeft(1)) position.toLeft(1) else null,
                    if(position.canMoveToBack(1)) position.toBack(1) else null
            )
            false -> listOf(
                    if(position.canMoveToBack(1)) position.toBack(1) else null,
                    if(position.toBack(1).canMoveToRight(1)) position.toBack(1).toRight(1) else null,
                    if(position.toBack(1).canMoveToLeft(1)) position.toBack(1).toLeft(1) else null,
                    if(position.canMoveToRight(1)) position.toRight(1) else null,
                    if(position.canMoveToLeft(1)) position.toLeft(1) else null,
                    if(position.canMoveToFront(1)) position.toFront(1) else null

            )
        }.filterNotNull()
    }

    override fun setPosition(position: Position): Piece {
        return Pawn(position, isBlack)
    }

    override fun mustPromote(toPosition: Position): Boolean {
        return false
    }

    override fun promote(): Piece {
        throw IllegalCallerException("promoted pawn cannot promote")
    }

    override fun toPieceInHand(): PieceInHand {
        return PieceInHand { position -> Pawn(position, !isBlack) }
    }
}
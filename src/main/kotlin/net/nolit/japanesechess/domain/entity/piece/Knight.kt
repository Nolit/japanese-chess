package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.entity.PieceInHand
import net.nolit.japanesechess.domain.value.Position

class Knight(override val position: Position, override val isBlack: Boolean): Piece(position, isBlack) {
    override fun listPositionAvailableToMove(): List<Position> {
        val canMoveToTwiceFront = position.canMoveToFront(2)
        val canMoveToRight = position.canMoveToRight(1)
        val canMoveToLeft = position.canMoveToLeft(1)
        val canMoveToTwiceBack = position.canMoveToBack(2)
        return when(isBlack) {
            true -> listOf(
                    if(canMoveToTwiceFront && canMoveToRight) position.toFront(2).toRight(1) else null,
                    if(canMoveToTwiceFront && canMoveToLeft) position.toFront(2).toLeft(1) else null
            )
            false -> listOf(
                    if(canMoveToTwiceBack && canMoveToRight) position.toBack(2).toRight(1) else null,
                    if(canMoveToTwiceBack && canMoveToLeft) position.toBack(2).toLeft(1) else null
            )
        }.filterNotNull()
    }

    override fun setPosition(position: Position): Piece {
        return Knight(position, isBlack)
    }

    override fun mustPromote(toPosition: Position): Boolean {
        return when(isBlack) {
            true -> toPosition.y < 1
            false -> toPosition.y > 7
        }
    }

    override fun promote(): PromotedKnight {
        return PromotedKnight(position, isBlack)
    }

    override fun toPieceInHand(): PieceInHand {
        return PieceInHand (getName()) { position -> Knight(position, !isBlack) }
    }

    override fun getName(): String {
        return "Knight"
    }
}
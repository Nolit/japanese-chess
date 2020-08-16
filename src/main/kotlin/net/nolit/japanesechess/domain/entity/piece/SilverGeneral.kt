package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.entity.PieceInHand
import net.nolit.japanesechess.domain.value.Position

class SilverGeneral(override val position: Position, override val isBlack: Boolean): Piece(position, isBlack) {
    override fun listPositionAvailableToMove(): List<Position> {
        val canMoveToFront = position.canMoveToFront(1)
        val canMoveToRight = position.canMoveToRight(1)
        val canMoveToLeft = position.canMoveToLeft(1)
        val canMoveToBack = position.canMoveToBack(1)
        return when(isBlack) {
            true -> listOf(
                    if(canMoveToFront) position.toFront(1) else null,
                    if(canMoveToFront && canMoveToRight) position.toFront(1).toRight(1) else null,
                    if(canMoveToFront && canMoveToLeft) position.toFront(1).toLeft(1) else null,
                    if(canMoveToBack && canMoveToRight) position.toBack(1).toRight(1) else null,
                    if(canMoveToBack && canMoveToLeft) position.toBack(1).toLeft(1) else null
            )
            false -> listOf(
                    if(canMoveToBack) position.toBack(1) else null,
                    if(canMoveToBack && canMoveToRight) position.toBack(1).toRight(1) else null,
                    if(canMoveToBack && canMoveToLeft) position.toBack(1).toLeft(1) else null,
                    if(canMoveToFront && canMoveToRight) position.toFront(1).toRight(1) else null,
                    if(canMoveToFront && canMoveToLeft) position.toFront(1).toLeft(1) else null

            )
        }.filterNotNull()
    }

    override fun setPosition(position: Position): Piece {
        return SilverGeneral(position, isBlack)
    }

    override fun mustPromote(toPosition: Position): Boolean {
        return false
    }

    override fun promote(): PromotedSilverGeneral {
        return PromotedSilverGeneral(position, isBlack)
    }

    override fun toPieceInHand(): PieceInHand {
        return PieceInHand (getName()) { position -> SilverGeneral(position, !isBlack) }
    }

    override fun getName(): String {
        return "SilverGeneral"
    }
}
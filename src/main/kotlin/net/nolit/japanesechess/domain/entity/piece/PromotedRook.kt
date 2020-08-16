package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.entity.PieceInHand
import net.nolit.japanesechess.domain.value.Position

class PromotedRook(override val position: Position, override val isBlack: Boolean): Piece(position, isBlack) {
    override fun listPositionAvailableToMove(): List<Position> {
        val canMoveToFront = position.canMoveToFront(1)
        val canMoveToRight = position.canMoveToRight(1)
        val canMoveToLeft = position.canMoveToLeft(1)
        val canMoveToBack = position.canMoveToBack(1)
        val upperAndLowerPositions = listOfNotNull(
            if (canMoveToFront) position.toFront(1) else null,
            if (canMoveToFront && canMoveToRight) position.toFront(1).toRight(1) else null,
            if (canMoveToFront && canMoveToLeft) position.toFront(1).toLeft(1) else null,
            if (canMoveToBack && canMoveToRight) position.toBack(1).toRight(1) else null,
            if (canMoveToBack && canMoveToLeft) position.toBack(1).toLeft(1) else null
        )

        val xFrontLinePositions = (1..8).toList()
                .filter { position.canMoveToFront(it) }
                .map { position.toFront(it) }
        val xBackLinePositions = (1..8).toList()
                .filter { position.canMoveToBack(it) }
                .map { position.toBack(it) }
        val yRightLinePositions = (1..8).toList()
                .filter { position.canMoveToRight(it) }
                .map { position.toRight(it) }
        val yLeftLinePositions = (1..8).toList()
                .filter { position.canMoveToLeft(it) }
                .map { position.toLeft(it) }

        return xFrontLinePositions
                .plus(xBackLinePositions)
                .plus(yRightLinePositions)
                .plus(yLeftLinePositions)
                .plus(upperAndLowerPositions)
    }

    override fun setPosition(position: Position): Piece {
        return PromotedRook(position, isBlack)
    }

    override fun mustPromote(toPosition: Position): Boolean {
        return false
    }

    override fun promote(): Piece {
        throw IllegalCallerException("PromotedRook cannot promote")
    }

    override fun toPieceInHand(): PieceInHand {
        return PieceInHand ("Rook") { position -> PromotedRook(position, !isBlack) }
    }

    override fun getName(): String {
        return "PromotedRook"
    }

    override fun canPromote(toPosition: Position): Boolean {
        return false
    }
}
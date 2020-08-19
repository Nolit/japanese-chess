package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.entity.PieceInHand
import net.nolit.japanesechess.domain.value.Position

class PromotedRook(override val position: Position, override val isBlack: Boolean): Piece(position, isBlack) {
    override fun listPositionAvailableToMove(): List<Position> {
        val upperAndLowerPositions = listOfNotNull(
            if (position.distanceFromTopRight > 0) position.toFront(1).toRight(1) else null,
            if (position.distanceFromBottomRight > 0) position.toBack(1).toRight(1) else null,
            if (position.distanceFromBottomLeft > 0) position.toBack(1).toLeft(1) else null,
            if (position.distanceFromTopLeft > 0) position.toFront(1).toLeft(1) else null
        )

        val xLinePositions = (-position.distanceFromLeftEnd..position.distanceFromRightEnd)
                .toList()
                .map { position.toRight(it) }
        val yLinePositions = (-position.distanceFromBackEnd..position.distanceFromFrontEnd)
                .toList()
                .map { position.toFront(it) }

        return upperAndLowerPositions
                .plus(xLinePositions)
                .plus(yLinePositions)
                .filter { position != it }
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
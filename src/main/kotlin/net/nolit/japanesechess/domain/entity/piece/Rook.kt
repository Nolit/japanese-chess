package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.entity.PieceInHand
import net.nolit.japanesechess.domain.value.Position

class Rook(override val position: Position, override val isBlack: Boolean): Piece(position, isBlack) {
    override fun listPositionAvailableToMove(): List<Position> {
        val xLinePositions = (-position.distanceFromLeftEnd..position.distanceFromRightEnd)
                .toList()
                .map { position.toRight(it) }
        val yLinePositions = (-position.distanceFromBackEnd..position.distanceFromFrontEnd)
                .toList()
                .map { position.toFront(it) }

        return xLinePositions
                .plus(yLinePositions)
                .filter { position != it }
    }

    override fun setPosition(position: Position): Piece {
        return Rook(position, isBlack)
    }

    override fun mustPromote(toPosition: Position): Boolean {
        return false
    }

    override fun promote(): PromotedRook {
        return PromotedRook(position, isBlack)
    }

    override fun toPieceInHand(): PieceInHand {
        return PieceInHand (getName()) { position -> Rook(position, !isBlack) }
    }

    override fun getName(): String {
        return "Rook"
    }
}
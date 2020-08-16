package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.entity.PieceInHand
import net.nolit.japanesechess.domain.value.Position

class Rook(override val position: Position, override val isBlack: Boolean): Piece(position, isBlack) {
    override fun listPositionAvailableToMove(): List<Position> {
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
        return xFrontLinePositions.plus(xBackLinePositions)
                                    .plus(yRightLinePositions)
                                    .plus(yLeftLinePositions)
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
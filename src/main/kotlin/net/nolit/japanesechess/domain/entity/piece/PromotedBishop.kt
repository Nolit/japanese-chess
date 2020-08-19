package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.entity.PieceInHand
import net.nolit.japanesechess.domain.value.Position

class PromotedBishop(override val position: Position, override val isBlack: Boolean): Piece(position, isBlack) {
    override fun listPositionAvailableToMove(): List<Position> {
        val leftRightFrontBack = listOfNotNull(
                if (position.canMoveToFront(1)) position.toFront(1) else null,
                if (position.canMoveToRight(1)) position.toRight(1) else null,
                if (position.canMoveToBack(1)) position.toBack(1) else null,
                if (position.canMoveToLeft(1)) position.toLeft(1) else null
        )

        //右上がりの移動可能範囲
        val positions1 = (-position.distanceFromBottomLeft..position.distanceFromTopRight)
                .toList()
                .map { position.toRight(it).toFront(it) }
        //左上がりの移動可能範囲
        val positions2 = (-position.distanceFromBottomRight..position.distanceFromTopLeft)
                .toList()
                .map { position.toLeft(it).toFront(it) }

        return leftRightFrontBack
                .plus(positions1)
                .plus(positions2)
                .filter { position != it }
    }

    override fun setPosition(position: Position): Piece {
        return PromotedBishop(position, isBlack)
    }

    override fun mustPromote(toPosition: Position): Boolean {
        return false
    }

    override fun promote(): Piece {
        throw IllegalCallerException("PromotedBishop cannot promote")
    }

    override fun toPieceInHand(): PieceInHand {
        return PieceInHand ("Bishop") { position -> Bishop(position, !isBlack) }
    }

    override fun getName(): String {
        return "PromotedBishop"
    }

    override fun canPromote(toPosition: Position): Boolean {
        return false
    }
}
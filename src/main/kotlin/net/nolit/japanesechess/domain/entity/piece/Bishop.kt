package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.entity.PieceInHand
import net.nolit.japanesechess.domain.value.Position

class Bishop(override val position: Position, override val isBlack: Boolean): Piece(position, isBlack) {
    override fun listPositionAvailableToMove(): List<Position> {
        //右上がりの移動可能範囲
        val positions1 = (-position.distanceFromBottomLeft..position.distanceFromTopRight)
                .toList()
                .map { position.toRight(it).toFront(it) }
        //左上がりの移動可能範囲
        val positions2 = (-position.distanceFromBottomRight..position.distanceFromTopLeft)
                .toList()
                .map { position.toLeft(it).toFront(it) }

        return positions1
                .plus(positions2)
                .filter { position != it }
    }

    override fun setPosition(position: Position): Piece {
        return Bishop(position, isBlack)
    }

    override fun mustPromote(toPosition: Position): Boolean {
        return false
    }

    override fun promote(): Piece {
        return PromotedBishop(position, isBlack)
    }

    override fun toPieceInHand(): PieceInHand {
        return PieceInHand (getName()) { position -> Bishop(position, !isBlack) }
    }

    override fun getName(): String {
        return "Bishop"
    }

    override fun canPromote(toPosition: Position): Boolean {
        return when(isBlack) {
            true -> position.inBlackPromotionArea() || toPosition.inBlackPromotionArea()
            false -> position.inWhitePromotionArea() || toPosition.inWhitePromotionArea()
        }
    }
}
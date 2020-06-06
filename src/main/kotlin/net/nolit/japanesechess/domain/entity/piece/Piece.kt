package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.entity.PieceInHand
import net.nolit.japanesechess.domain.value.Position

abstract class Piece(private val position: Position, private val isBlack: Boolean) {
    abstract fun listPositionAvailableToMove(): List<Position>

    fun move(position: Position): Piece {
        val positionsAvailableToMove = this.listPositionAvailableToMove()
        if (! positionsAvailableToMove.contains(position)) {
            throw IllegalArgumentException("this piece cannot move there")
        }
        return setPosition(position)
    }

    //TODO: できれば親クラスで処理を定義したいけどインスタンス化できない
    abstract fun setPosition(position: Position): Piece

    abstract fun mustPromote(toPosition: Position): Boolean

    fun canPromote(toPosition: Position): Boolean {
        return position.isInPromotionArea() || toPosition.isInPromotionArea()
    }

    abstract fun promote(): Piece

    abstract fun toPieceInHand(): PieceInHand
}
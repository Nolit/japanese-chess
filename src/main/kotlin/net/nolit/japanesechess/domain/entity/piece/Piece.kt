package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.entity.PieceInHand
import net.nolit.japanesechess.domain.value.Position

abstract class Piece(open val position: Position, open val isBlack: Boolean) {
    //TODO: listAvailableDestinationに変更
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

    /**
     * このメソッドは現在のpositionから引数に渡されたpositionへ移動した時に成れるかを判定します
     * 成れない駒はこのメソッドをオーバーライドしてfalseを返してください
     */
    open fun canPromote(toPosition: Position): Boolean {
        return when(isBlack) {
            true -> position.inBlackPromotionArea() || toPosition.inBlackPromotionArea()
            false -> position.inWhitePromotionArea() || toPosition.inWhitePromotionArea()
        }
    }

    abstract fun promote(): Piece

    abstract fun toPieceInHand(): PieceInHand

    abstract fun getName(): String
}
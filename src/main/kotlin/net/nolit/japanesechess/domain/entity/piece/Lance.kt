package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.entity.PieceInHand
import net.nolit.japanesechess.domain.value.Position

class Lance(override val position: Position, override val isBlack: Boolean): Piece(position, isBlack) {
    override fun listPositionAvailableToMove(): List<Position> {
        return when(isBlack) {
            true -> (1..8).toList()
                    .filter { position.canMoveToFront(it) }
                    .map { position.toFront(it) }
            false -> (1..8).toList()
                    .filter { position.canMoveToBack(it) }
                    .map { position.toBack(it) }
        }
    }

    override fun setPosition(position: Position): Piece {
        return Lance(position, isBlack)
    }

    override fun mustPromote(toPosition: Position): Boolean {
        return when(isBlack) {
            true -> toPosition.y==0
            false -> toPosition.y==8
        }
    }

    override fun promote(): PromotedLance {
        return PromotedLance(position, isBlack)
    }

    override fun toPieceInHand(): PieceInHand {
        return PieceInHand (getName()) { position -> Lance(position, !isBlack) }
    }

    override fun getName(): String {
        return "Lance"
    }
}
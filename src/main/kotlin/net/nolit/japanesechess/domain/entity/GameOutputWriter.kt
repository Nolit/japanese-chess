package net.nolit.japanesechess.domain.entity

import net.nolit.japanesechess.domain.entity.piece.Piece

interface GameOutputWriter {
    fun output(piecesOnBoard: List<Piece>, piecesInHandOnBlack: List<PieceInHand>, piecesInHandOnWhite: List<PieceInHand>)
}
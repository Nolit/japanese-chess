package net.nolit.japanesechess.domain.repository

import net.nolit.japanesechess.domain.entity.BoardInitializable
import net.nolit.japanesechess.domain.entity.piece.*
import net.nolit.japanesechess.domain.value.Position

class BoardDefaultRepository: BoardInitializable {
    override fun initialize(): MutableList<Piece> {
        return mutableListOf(
                Pawn(Position(0,2), false),
                Pawn(Position(1,2), false),
                Pawn(Position(2,2), false),
                Pawn(Position(3,2), false),
                Pawn(Position(4,2), false),
                Pawn(Position(5,2), false),
                Pawn(Position(6,2), false),
                Pawn(Position(7,2), false),
                Pawn(Position(8,2), false),
                Pawn(Position(0,6), true),
                Pawn(Position(1,6), true),
                Pawn(Position(2,6), true),
                Pawn(Position(3,6), true),
                Pawn(Position(4,6), true),
                Pawn(Position(5,6), true),
                Pawn(Position(6,6), true),
                Pawn(Position(7,6), true),
                Pawn(Position(8,6), true),
                Lance(Position(0,0), false),
                Lance(Position(8,0), false),
                Lance(Position(0,8), true),
                Lance(Position(8,8), true),
                Knight(Position(1,0), false),
                Knight(Position(7,0), false),
                Knight(Position(1,8), true),
                Knight(Position(7,8), true),
                SilverGeneral(Position(2,0), false),
                SilverGeneral(Position(6,0), false),
                SilverGeneral(Position(2,8), true),
                SilverGeneral(Position(6,8), true),
                GoldGeneral(Position(3,0), false),
                GoldGeneral(Position(5,0), false),
                GoldGeneral(Position(3,8), true),
                GoldGeneral(Position(5,8), true),
                Rook(Position(1,1), false),
                Rook(Position(7,7), true),
                Bishop(Position(7,1), false),
                Bishop(Position(1,7), true),
                King(Position(4,0), false),
                King(Position(4,8), true)
        )
    }
}
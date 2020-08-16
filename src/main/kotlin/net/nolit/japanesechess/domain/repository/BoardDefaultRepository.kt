package net.nolit.japanesechess.domain.repository

import net.nolit.japanesechess.domain.entity.BoardInitializable
import net.nolit.japanesechess.domain.entity.piece.Knight
import net.nolit.japanesechess.domain.entity.piece.Lance
import net.nolit.japanesechess.domain.entity.piece.Pawn
import net.nolit.japanesechess.domain.entity.piece.Piece
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
                Knight(Position(7,8), true)
        )
    }
}
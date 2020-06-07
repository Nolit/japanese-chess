package net.nolit.japanesechess.domain.piece

import net.nolit.japanesechess.domain.entity.piece.Pawn
import net.nolit.japanesechess.domain.value.Position
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PawnTest {
    @Test
    fun testListPositionAvailableToMove() {
        //先手
        val blackPawn = Pawn(Position(3, 3), true)
        assertEquals(listOf(Position(3, 4)), blackPawn.listPositionAvailableToMove())
        //後手
        val whitePawn = Pawn(Position(3, 3), false)
        assertEquals(listOf(Position(3, 2)), whitePawn.listPositionAvailableToMove())
    }

    @Test
    fun testSetPosition() {
        val blackPawn = Pawn(Position(3, 3), true)
        val movedBlackPawn = blackPawn.setPosition(Position(3, 4))
        assertEquals(Position(3, 4), movedBlackPawn.position)
        assertEquals(blackPawn.isBlack, movedBlackPawn.isBlack)
    }

    @Test
    fun testMustPromote() {
        val blackPawn = Pawn(Position(3, 3), true)
        val whitePawn = Pawn(Position(3, 3), false)
        val firstRow = Position(0, 0)
        val lastRow = Position(0, 8)
        //先手 1列目
        assertEquals(true, blackPawn.mustPromote(firstRow))
        //後手 1列目
        assertEquals(false, whitePawn.mustPromote(firstRow))
        //先手 9列目
        assertEquals(false, blackPawn.mustPromote(lastRow))
        //後手 9列目
        assertEquals(true, whitePawn.mustPromote(lastRow))
    }

    @Test
    fun testPromote() {
        val position = Position(3, 3)
        val isBlack = true
        val promotedPawn = Pawn(position, isBlack).promote()
        //同じposition
        assertEquals(promotedPawn.position, position)
        //同じisBlack
        assertEquals(promotedPawn.isBlack, isBlack)
    }

    @Test
    fun testToPieceInHand() {
        val blackPawn = Pawn(Position(3, 3), true)
        val pieceInHand = blackPawn.toPieceInHand()
        val droppedPiece = pieceInHand.drop(Position(3, 3))
        //先手/後手は元の駒の反対になる
        assertEquals(false, droppedPiece.isBlack)
        //dropメソッドに渡したpositionを持つ
        assertEquals(Position(3, 3), droppedPiece.position)
    }
}
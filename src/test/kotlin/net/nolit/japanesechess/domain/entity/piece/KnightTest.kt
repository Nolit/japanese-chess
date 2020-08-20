package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.value.Position
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class KnightTest {
    @Test
    fun testListPositionAvailableToMove() {
        //先手
        val blackKnight = Knight(Position(5, 5), true)
        assertIterableEquals(listOf(
            Position(6, 3),
            Position(4, 3)
        ), blackKnight.listPositionAvailableToMove())

        //後手
        val whiteKnight = Knight(Position(3, 5), false)
        assertEquals(listOf(
            Position(4, 7),
            Position(2, 7)
        ), whiteKnight.listPositionAvailableToMove())
    }

    @Test
    fun testSetPosition() {
        val blackKnight = Knight(Position(3, 3), true)
        val movedBlackKnight = blackKnight.setPosition(Position(4, 1))
        assertEquals(Position(4, 1), movedBlackKnight.position)
        assertEquals(blackKnight.isBlack, movedBlackKnight.isBlack)
    }

    @Test
    fun testMustPromote() {
        val blackKnight = Knight(Position(3, 3), true)
        val whiteKnight = Knight(Position(3, 3), false)
        val firstRow = Position(3, 0)
        val lastRow = Position(3, 8)
        //先手 1列目
        assertEquals(true, blackKnight.mustPromote(firstRow))
        //後手 1列目
        assertEquals(false, whiteKnight.mustPromote(firstRow))
        //先手 9列目
        assertEquals(false, blackKnight.mustPromote(lastRow))
        //後手 9列目
        assertEquals(true, whiteKnight.mustPromote(lastRow))
    }

    @Test
    fun testPromote() {
        val position = Position(3, 2)
        val isBlack = true
        val promotedKnight = Knight(position, isBlack).promote()
        //同じposition
        assertEquals(promotedKnight.position, position)
        //同じisBlack
        assertEquals(promotedKnight.isBlack, isBlack)
    }

    @Test
    fun testToPieceInHand() {
        val isBlack = true
        val blackKnight = Knight(Position(3, 3), isBlack)
        val pieceInHand = blackKnight.toPieceInHand()
        val droppedPiece = pieceInHand.drop(Position(3, 3))
        //先手/後手は元の駒の反対になる
        assertEquals(!isBlack, droppedPiece.isBlack)
        //dropメソッドに渡したpositionを持つ
        assertEquals(Position(3, 3), droppedPiece.position)
    }

    @Test
    fun testGetName() {
        val knight = Knight(Position(3, 3), true)
        assertEquals(knight.getName(), "Knight")
    }
}
package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.value.Position
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LanceTest {
    @Test
    fun testListPositionAvailableToMove() {
        //先手
        val blackLance = Lance(Position(3, 2), true)
        assertIterableEquals(listOf(
            Position(3, 1),
            Position(3, 0)
        ), blackLance.listPositionAvailableToMove())

        //後手
        val whiteLance = Lance(Position(3, 6), false)
        assertEquals(listOf(
            Position(3, 7),
            Position(3, 8)
        ), whiteLance.listPositionAvailableToMove())
    }

    @Test
    fun testSetPosition() {
        val blackLance = Lance(Position(3, 3), true)
        val movedBlackLance = blackLance.setPosition(Position(3, 1))
        assertEquals(Position(3, 1), movedBlackLance.position)
        assertEquals(blackLance.isBlack, movedBlackLance.isBlack)
    }

    @Test
    fun testMustPromote() {
        val blackLance = Lance(Position(3, 3), true)
        val whiteLance = Lance(Position(3, 3), false)
        val firstRow = Position(3, 0)
        val lastRow = Position(3, 8)
        //先手 1列目
        assertEquals(true, blackLance.mustPromote(firstRow))
        //後手 1列目
        assertEquals(false, whiteLance.mustPromote(firstRow))
        //先手 9列目
        assertEquals(false, blackLance.mustPromote(lastRow))
        //後手 9列目
        assertEquals(true, whiteLance.mustPromote(lastRow))
    }

    @Test
    fun testPromote() {
        val position = Position(3, 3)
        val isBlack = true
        val promotedLance = Lance(position, isBlack).promote()
        //同じposition
        assertEquals(promotedLance.position, position)
        //同じisBlack
        assertEquals(promotedLance.isBlack, isBlack)
    }

    @Test
    fun testToPieceInHand() {
        val isBlack = true
        val blackLance = Lance(Position(3, 3), isBlack)
        val pieceInHand = blackLance.toPieceInHand()
        val droppedPiece = pieceInHand.drop(Position(3, 3))
        //先手/後手は元の駒の反対になる
        assertEquals(!isBlack, droppedPiece.isBlack)
        //dropメソッドに渡したpositionを持つ
        assertEquals(Position(3, 3), droppedPiece.position)
    }

    @Test
    fun testGetName() {
        val lance = Lance(Position(3, 3), true)
        assertEquals(lance.getName(), "Lance")
    }
}
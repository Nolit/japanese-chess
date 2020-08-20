package net.nolit.japanesechess.domain.entity.piece

import net.nolit.japanesechess.domain.value.Position
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SilverGeneralTest {
    @Test
    fun testListPositionAvailableToMove() {
        //先手
        val blackSilverGeneral = SilverGeneral(Position(5, 5), true)
        assertIterableEquals(listOf(
            Position(5, 4),
            Position(6, 4),
            Position(4, 4),
            Position(6, 6),
            Position(4, 6)
        ), blackSilverGeneral.listPositionAvailableToMove())

        //後手
        val whiteSilverGeneral = SilverGeneral(Position(5, 5), false)
        assertEquals(listOf(
            Position(5, 6),
            Position(6, 6),
            Position(4, 6),
            Position(6, 4),
            Position(4, 4)
        ), whiteSilverGeneral.listPositionAvailableToMove())
    }

    @Test
    fun testSetPosition() {
        val blackSilverGeneral = SilverGeneral(Position(3, 3), true)
        val movedBlackSilverGeneral = blackSilverGeneral.setPosition(Position(4, 2))
        assertEquals(Position(4, 2), movedBlackSilverGeneral.position)
        assertEquals(blackSilverGeneral.isBlack, movedBlackSilverGeneral.isBlack)
    }

    @Test
    fun testMustPromote() {
        val blackSilverGeneral = SilverGeneral(Position(3, 3), true)
        val whiteSilverGeneral = SilverGeneral(Position(3, 3), false)
        val firstRow = Position(3, 0)
        val lastRow = Position(3, 8)
        //先手 1列目
        assertEquals(false, blackSilverGeneral.mustPromote(firstRow))
        //後手 9列目
        assertEquals(false, whiteSilverGeneral.mustPromote(lastRow))
    }

    @Test
    fun testPromote() {
        val position = Position(3, 2)
        val isBlack = true
        val promotedSilverGeneral = SilverGeneral(position, isBlack).promote()
        //同じposition
        assertEquals(promotedSilverGeneral.position, position)
        //同じisBlack
        assertEquals(promotedSilverGeneral.isBlack, isBlack)
    }

    @Test
    fun testToPieceInHand() {
        val isBlack = true
        val blackSilverGeneral = SilverGeneral(Position(3, 3), isBlack)
        val pieceInHand = blackSilverGeneral.toPieceInHand()
        val droppedPiece = pieceInHand.drop(Position(3, 3))
        //先手/後手は元の駒の反対になる
        assertEquals(!isBlack, droppedPiece.isBlack)
        //dropメソッドに渡したpositionを持つ
        assertEquals(Position(3, 3), droppedPiece.position)
    }

    @Test
    fun testGetName() {
        val silverGeneral = SilverGeneral(Position(3, 3), true)
        assertEquals(silverGeneral.getName(), "SilverGeneral")
    }
}
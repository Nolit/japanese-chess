package net.nolit.japanesechess.domain.value

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PositionTest {
    @Test
    fun testDistanceFromFrontEnd() {
        val position = Position(3,4)
        Assertions.assertEquals(position.distanceFromFrontEnd, 4)
    }

    @Test
    fun testDistanceFromBackEnd() {
        val position = Position(3,4)
        Assertions.assertEquals(position.distanceFromBackEnd, 4)
    }

    @Test
    fun testDistanceFromRightEnd() {
        val position = Position(3,4)
        Assertions.assertEquals(position.distanceFromRightEnd, 5)
    }

    @Test
    fun testDistanceFromLeftEnd() {
        val position = Position(3,4)
        Assertions.assertEquals(position.distanceFromLeftEnd, 3)
    }

    @Test
    fun testDistanceFromTopRight() {
        val position = Position(2,4)
        Assertions.assertEquals(position.distanceFromTopRight, 4)
    }

    @Test
    fun testDistanceFromBottomRight() {
        val position = Position(5,2)
        Assertions.assertEquals(position.distanceFromBottomRight, 3)
    }

    @Test
    fun testDistanceFromBottomLeft() {
        val position = Position(1,7)
        Assertions.assertEquals(position.distanceFromBottomLeft, 1)
    }

    @Test
    fun testDistanceFromTopLeft() {
        val position = Position(4,5)
        Assertions.assertEquals(position.distanceFromTopLeft, 4)
    }

    @Test
    fun testInBlackPromotionArea() {
        Assertions.assertEquals(Position(4,2).inBlackPromotionArea(), true)
        Assertions.assertEquals(Position(4,4).inBlackPromotionArea(), false)
    }

    @Test
    fun testInWhitePromotionArea() {
        Assertions.assertEquals(Position(4,8).inWhitePromotionArea(), true)
        Assertions.assertEquals(Position(4,4).inWhitePromotionArea(), false)
    }

    @Test
    fun testToRight() {
        Assertions.assertEquals(Position(4,5).toRight(3), Position(7,5))
    }

    @Test
    fun testToLeft() {
        Assertions.assertEquals(Position(4,5).toLeft(2), Position(2,5))
    }

    @Test
    fun testToFront() {
        Assertions.assertEquals(Position(4,5).toFront(3), Position(4,2))
    }

    @Test
    fun testToBack() {
        Assertions.assertEquals(Position(4,5).toBack(3), Position(4,8))
    }

    @Test
    fun testCanMoveToRight() {
        Assertions.assertEquals(Position(4,5).canMoveToRight(4), true)
        Assertions.assertEquals(Position(4,5).canMoveToRight(5), false)
    }

    @Test
    fun testCanMoveToLeft() {
        Assertions.assertEquals(Position(4,5).canMoveToLeft(4), true)
        Assertions.assertEquals(Position(4,5).canMoveToLeft(5), false)
    }

    @Test
    fun testCanMoveToFront() {
        Assertions.assertEquals(Position(4,5).canMoveToFront(5), true)
        Assertions.assertEquals(Position(4,5).canMoveToFront(6), false)
    }

    @Test
    fun testCanMoveToBack() {
        Assertions.assertEquals(Position(4,5).canMoveToBack(3), true)
        Assertions.assertEquals(Position(4,5).canMoveToBack(4), false)
    }
}